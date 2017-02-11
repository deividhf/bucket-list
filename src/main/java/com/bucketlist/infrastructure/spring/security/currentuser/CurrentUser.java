package com.bucketlist.infrastructure.spring.security.currentuser;

import org.springframework.security.core.authority.AuthorityUtils;

import com.bucketlist.infrastructure.spring.security.user.model.Role;
import com.bucketlist.infrastructure.spring.security.user.model.User;

@SuppressWarnings("serial")
public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private final User user;
	
	public CurrentUser(final User user) {
		super(user.getName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
		this.user = user;
	}
	
	public Long getId() {
		return user.getId();
	}
	
	public Role getRole() {
		return user.getRole();
	}
}
