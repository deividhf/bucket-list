package com.bucketlist.infrastructure.spring.security.user.model;

import static com.bucketlist.infrastructure.spring.security.user.model.UserSpecification.checkUniqueUser;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bucketlist.domain.shared.specification.AbstractSpecification;
import com.bucketlist.domain.shared.specification.Validator;

@Component
public class UserValidator extends Validator<User> {

	private UserRepository userRepository;
	
	public UserValidator(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<AbstractSpecification<User>> specifications() {
		return Arrays.asList(checkUniqueUser(userRepository));
	}

}
