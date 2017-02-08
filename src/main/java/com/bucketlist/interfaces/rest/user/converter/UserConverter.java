package com.bucketlist.interfaces.rest.user.converter;

import org.springframework.stereotype.Component;

import com.bucketlist.domain.model.email.Email;
import com.bucketlist.domain.model.user.User;
import com.bucketlist.domain.model.user.User.Builder;
import com.bucketlist.interfaces.rest.user.dto.UserDTO;
import com.bucketlist.interfaces.shared.AbstractConverter;

@Component
public class UserConverter extends AbstractConverter<User, UserDTO, User.Builder> {

	@Override
	public User toEntity(final UserDTO representation, final Builder builder) {
		return builder.withName(representation.getName())
				.withPassword(representation.getPassword())
				.withEmail(Email.of(representation.getEmail()))
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
