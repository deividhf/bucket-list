package com.bucketlist.interfaces.rest.user.converter;

import org.springframework.stereotype.Component;

import com.bucketlist.domain.model.email.Email;
import com.bucketlist.domain.model.user.User;
import com.bucketlist.domain.model.user.User.Builder;
import com.bucketlist.interfaces.rest.user.dto.UserDTO;
import com.bucketlist.interfaces.shared.AbstractConverter;

@Component
public class ChangeUserConverter extends AbstractConverter<User, UserDTO, User.Builder> {

	@Override
	public User toEntity(final UserDTO representation, final Builder builder) {
		return builder.withEmail(Email.of(representation.getEmail()))
				.withName(representation.getName())
				.build();
	}

	@Override
	public UserDTO toRepresentation(final User user) {
		return UserDTO.builder()
				.withEmail(user.getEmail()
						.map(Email::getAddress)
						.orElse(null))
				.withName(user.getName())
				.build();
	}

}
