package com.bucketlist.domain.model.bucketlist.goal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import javax.persistence.Embeddable;

import com.google.common.base.MoreObjects;

@Embeddable
public class Goal {

	private String description;
	
	Goal() {
	}

	private Goal(final String description) {
		this.description = description;
	}
	
	public static Goal of(String description) {
		checkNotNull(description);
		return new Goal(description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof Goal) {
			final Goal other = (Goal) obj;
			return Objects.equals(this.description, other.description);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("description", description)
				.toString();
	}
	
	public String getDescription() {
		return description;
	}
}
