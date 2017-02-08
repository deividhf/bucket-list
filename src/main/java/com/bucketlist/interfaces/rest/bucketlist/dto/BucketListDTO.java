package com.bucketlist.interfaces.rest.bucketlist.dto;

import javax.validation.constraints.NotNull;

import com.bucketlist.interfaces.rest.user.dto.UserDTO;

public class BucketListDTO {

	@NotNull
	private UserDTO user;
	@NotNull
	private String description;
	
	BucketListDTO() {
	}
	
	private BucketListDTO(final UserDTO user, final String description) {
		this.user = user;
		this.description = description;
	}

	public UserDTO getUser() {
		return user;
	}
	public String getDescription() {
		return description;
	}
	
	public static final BucketListDTOBuilder builder() {
		return new BucketListDTOBuilder();
	}
	
	public static final class BucketListDTOBuilder {
		
		private UserDTO user;
		private String description;
		
		private BucketListDTOBuilder() {
		}
		
		public BucketListDTOBuilder withUser(final UserDTO user) {
			this.user = user;
			return this;
		}
		
		public BucketListDTOBuilder withDescription(final String description) {
			this.description = description;
			return this;
		}
		
		public BucketListDTO build() {
			return new BucketListDTO(user, description); 
		}
	}
}
