package com.bucketlist.infrastructure.spring.security.user.model;

import static com.bucketlist.infrastructure.spring.security.user.model.QUser.user;

import com.bucketlist.domain.shared.specification.AbstractSpecification;
import com.querydsl.core.BooleanBuilder;

public class UserSpecification {
	
	public static AbstractSpecification<User> checkUniqueUser(final UserRepository userRepository) {
		return new AbstractSpecification<User>("Duplicate User") {
			
			@Override
			public boolean isSatisfiedBy(final User entity) {
				final BooleanBuilder builder = new BooleanBuilder();
				if(entity.getId() != null) builder.andNot(user.id.eq(entity.getId()));
				builder.and((user.name.equalsIgnoreCase(entity.getName())));
				return !userRepository.exists(builder);
			}
			
		};
	}
}