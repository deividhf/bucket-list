package com.bucketlist.infrastructure.spring.security.user.model;

public class UserCreatedEvent {

	private final User user;

	public UserCreatedEvent(final User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}
