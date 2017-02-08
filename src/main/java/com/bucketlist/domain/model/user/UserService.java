package com.bucketlist.domain.model.user;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.BadRequestException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	public User findUserIfAuthenticated(final String name) {
		if(!findUserAuthenticated().getName().equals(name)) {
			throw new BadRequestException("Action not permitted");
		}
		return findByUserName(name);
	}

	private User findUserAuthenticated() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getName() == null) {
			throw new BadRequestException("There' a user authenticated");
		}
		return findByUserName(authentication.getName());
	}

	public User findByUserName(final String userNameToFind) {
		return userRepository.findByName(userNameToFind)
				.orElseThrow(() -> new EntityNotFoundException("User not found with the name " + userNameToFind));
	}
}