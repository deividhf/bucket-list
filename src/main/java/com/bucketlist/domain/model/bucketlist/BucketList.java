package com.bucketlist.domain.model.bucketlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bucketlist.domain.model.bucketlist.goal.Goal;
import com.bucketlist.domain.shared.AbstractBuilder;
import com.bucketlist.domain.shared.AbstractEntity;
import com.bucketlist.infrastructure.spring.security.user.model.User;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

@SuppressWarnings("serial")
@Entity
@Table(name = "bucket_list")
public class BucketList extends AbstractEntity {

	@OneToOne
	@JoinColumn(name = "user_id")
	@NotNull
	private User user;
	@NotNull
	@Size(max = 100)
	private String description;
	@ElementCollection
	@CollectionTable(name = "goal", joinColumns = {@JoinColumn(name = "bucket_list_id")})
	private List<Goal> goals = new ArrayList<>();
	
	BucketList() {
	}
	
	private BucketList(final Long id, final User user, final String description, final List<Goal> goals) {
		super(id);
		this.user = user;
		this.description = description;
		this.goals = ImmutableList.copyOf(goals);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, description);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BucketList) {
			final BucketList other = (BucketList) obj;
			return Objects.equals(this.user, other.user)
					&& Objects.equals(this.description, other.description);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("user", user)
				.add("description", description)
				.toString();
	}

	public User getUser() {
		return user;
	}

	public String getDescription() {
		return description;
	}
	
	public List<Goal> getGoals() {
		return ImmutableList.copyOf(goals);
	}
	
	public static final class Builder extends AbstractBuilder<BucketList> {

		private Builder(final BucketList entity) {
			super(entity);
		}
		
		public static final Builder create() {
			return new Builder(new BucketList());
		}
		
		public static final Builder from(final BucketList bucketList) {
			return new Builder(new BucketList(bucketList.id, bucketList.user, bucketList.description, 
					bucketList.goals));
		}
		
		public Builder forUser(final User user) {
			entity.user = user;
			return this;
		}
		
		public Builder withDescription(final String description) {
			entity.description = description;
			return this;
		}
		
		public Builder withGoals(final List<Goal> goals) {
			entity.goals = goals;
			return this;
		}
	}
}
