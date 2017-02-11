package com.bucketlist.shared;

import com.bucketlist.infrastructure.spring.security.user.rest.dto.UserDTO;

public class UserWithPasswordTest {

	private UserDTO user;
	private String password;
	
	public UserWithPasswordTest(final UserDTO user, final String password) {
		this.user = user;
		this.password = password;
	}

	public String getName() {
		return user.getName();
	}

	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
}
