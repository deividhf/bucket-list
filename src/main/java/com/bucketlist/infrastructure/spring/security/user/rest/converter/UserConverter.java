package com.bucketlist.infrastructure.spring.security.user.rest.converter;

import org.springframework.stereotype.Component;

import com.bucketlist.infrastructure.spring.security.user.model.User;
import com.bucketlist.infrastructure.spring.security.user.model.User.Builder;
import com.bucketlist.infrastructure.spring.security.user.model.email.Email;
import com.bucketlist.infrastructure.spring.security.user.rest.dto.UserDTO;
import com.bucketlist.interfaces.shared.AbstractConverter;

@Component
public class UserConverter extends AbstractConverter<User, UserDTO, User.Builder> {

	@Override
	public User toEntity(final UserDTO representation, final Builder builder) {
		return builder.withName(representation.getName())
				.withPassword(representation.getPassword())
				.withEmail(representation.getEmail().map(e -> Email.of(e)).orElse(null))
				.build();
	}

	@Override
	public UserDTO toRepresentation(final User user) {
		return UserDTO.builder()
				.withName(user.getName())
				.withEmail(user.getEmail()
						.map(Email::getAddress)
						.orElse(null))
				.build();
	}

}
