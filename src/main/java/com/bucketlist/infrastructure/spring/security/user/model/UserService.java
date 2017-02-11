package com.bucketlist.infrastructure.spring.security.user.model;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(final UserRepository userRepository) {
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
}