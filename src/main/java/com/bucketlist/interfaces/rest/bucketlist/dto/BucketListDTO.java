package com.bucketlist.interfaces.rest.bucketlist.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.bucketlist.infrastructure.spring.security.user.rest.dto.UserDTO;
import com.bucketlist.interfaces.rest.bucketlist.goal.dto.GoalDTO;
import com.google.common.collect.ImmutableList;

public class BucketListDTO {

	private Long identifier;
	@NotNull
	private UserDTO user;
	@NotNull
	private String description;
	private List<GoalDTO> goals;
	
	BucketListDTO() {
	}
	
	private BucketListDTO(final Long identifier, final UserDTO user, final String description, 
			final List<GoalDTO> goals) {
		this.identifier = identifier;
		this.user = user;
		this.description = description;
		this.goals = goals;
	}
	
	public Long getIdentifier() {
		return identifier;
	}

	public UserDTO getUser() {
		return user;
	}
	public String getDescription() {
		return description;
	}
	
	public List<GoalDTO> getGoals() {
		return ImmutableList.copyOf(goals);
	}
	
	public static final BucketListDTOBuilder builder() {
		return new BucketListDTOBuilder();
	}
	

	public BucketListDTO withDescription(final String description) {
		return new BucketListDTO(this.identifier, this.user, description, this.goals);
	}
	
	public BucketListDTO withGoals(final List<GoalDTO> goals) {
		return new BucketListDTO(this.identifier, this.user, this.description, goals);
	}
	
	public static final class BucketListDTOBuilder {
		
		private Long identifier;
		private UserDTO user;
		private String description;
		private List<GoalDTO> goals;
		
		private BucketListDTOBuilder() {
		}
		
		public BucketListDTOBuilder withIdentifier(final Long identifier) {
			this.identifier = identifier;
			return this;
		}
		
		public BucketListDTOBuilder withUser(final UserDTO user) {
			this.user = user;
			return this;
		}
		
		public BucketListDTOBuilder withDescription(final String description) {
			this.description = description;
			return this;
		}
		
		public BucketListDTOBuilder withGoals(final List<GoalDTO> goals) {
			this.goals = goals;
			return this;
		}
		
		public BucketListDTO build() {
			return new BucketListDTO(identifier, user, description, goals); 
		}
	}
}
