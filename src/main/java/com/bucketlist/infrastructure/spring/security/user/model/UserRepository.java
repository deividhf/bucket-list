package com.bucketlist.infrastructure.spring.security.user.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {

	Optional<User> findByName(final String name);
}
