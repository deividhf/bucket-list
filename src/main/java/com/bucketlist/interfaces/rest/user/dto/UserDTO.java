package com.bucketlist.interfaces.rest.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDTO {

	@NotNull
	private String name;
	private String email;
	@NotNull
	@Size(max = 16)
	private String password;

	UserDTO() {
	}

	private UserDTO(final String name, final String email, final String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public UserDTO withPassword(final String password) {
		return new UserDTO(this.name, this.email, password);
	}
	
	public UserDTO withEmail(final String email) {
		return new UserDTO(this.name, email, this.password);
	}
	
	public static UserDTOBuilder builder() {
		return new UserDTOBuilder();
	}

	public static final class UserDTOBuilder {

		private String name;
		private String email;
		private String password;

		private UserDTOBuilder() {
		}

		public UserDTOBuilder withName(final String name) {
			this.name = name;
			return this;
		}

		public UserDTOBuilder withEmail(final String email) {
			this.email = email;
			return this;
		}

		public UserDTOBuilder withPassword(final String password) {
			this.password = password;
			return this;
		}

		public UserDTO build() {
			return new UserDTO(name, email, password);
		}
	}

}
