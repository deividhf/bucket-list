package com.bucketlist.domain.model.user.predicate;

import static com.bucketlist.domain.model.user.QUser.user;

import com.querydsl.core.types.dsl.BooleanExpression;

public class UserPredicate {

	public static BooleanExpression withId(final Long id) {
		return user.id.eq(id);
	}
	
	public static BooleanExpression withName(final String name) {
		return user.name.equalsIgnoreCase(name);
	}
	
	public static BooleanExpression withEmail(final String address) {
		return user.email.address.equalsIgnoreCase(address);
	}
}
