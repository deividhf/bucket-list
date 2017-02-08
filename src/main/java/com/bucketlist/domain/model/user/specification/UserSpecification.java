package com.bucketlist.domain.model.user.specification;

import static com.bucketlist.domain.model.user.predicate.UserPredicate.*;

import com.bucketlist.domain.model.user.User;
import com.bucketlist.domain.model.user.UserRepository;
import com.bucketlist.domain.shared.specification.AbstractSpecification;
import com.querydsl.core.BooleanBuilder;

public class UserSpecification {
	
	public static AbstractSpecification<User> checkUniqueUser(final UserRepository userRepository) {
		return new AbstractSpecification<User>("Duplicate User") {
			
			@Override
			public boolean isSatisfiedBy(final User entity) {
				final BooleanBuilder builder = new BooleanBuilder();
				if(entity.getId() != null) builder.and(withId(entity.getId()).not());
				builder.and((withName(entity.getName())));
				return !userRepository.exists(builder);
			}
			
		};
	}
}
