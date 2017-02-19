package com.bucketlist.interfaces.rest.bucketlist.dto;

import javax.validation.constraints.NotNull;

import com.bucketlist.infrastructure.spring.security.user.rest.dto.UserDTO;

public class BucketListDTO {

	private Long identifier;
	@NotNull
	private UserDTO user;
	@NotNull
	private String description;
	
	BucketListDTO() {
	}
	
	private BucketListDTO(final Long identifier, final UserDTO user, final String description) {
		this.identifier = identifier;
		this.user = user;
		this.description = description;
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
	
	public static final BucketListDTOBuilder builder() {
		return new BucketListDTOBuilder();
	}
	

	public BucketListDTO withDescription(final String description) {
		return new BucketListDTO(this.identifier, this.user, description);
	}
	
	public static final class BucketListDTOBuilder {
		
		private Long identifier;
		private UserDTO user;
		private String description;
		
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
		
		public BucketListDTO build() {
			return new BucketListDTO(identifier, user, description); 
		}
	}
}
