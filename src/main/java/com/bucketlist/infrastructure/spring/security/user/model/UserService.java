package com.bucketlist.infrastructure.spring.security.user.model;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private ApplicationEventPublisher eventPublisher;
	private UserRepository userRepository;

	public UserService(final ApplicationEventPublisher eventPublisher, final UserRepository userRepository) {
		this.eventPublisher = eventPublisher;
		this.userRepository = userRepository;
	}

	public User changePassword(final String userName, final String newPassword) {
		final User user = userRepository.findByName(userName)
				.orElseThrow(() -> new EntityNotFoundException("User not found with name " + userName));
		return userRepository.save(user.changePassword(newPassword));
	}
	
	public User findByName(final String userNameToFind) {
		return userRepository.findByName(userNameToFind)
				.orElseThrow(() -> new EntityNotFoundException("User not found with the name " + userNameToFind));
	}

	public User save(final User user) {
		final User userCreated = userRepository.save(user);
		eventPublisher.publishEvent(new UserCreatedEvent(userCreated));
		return userCreated;
	}
	
}