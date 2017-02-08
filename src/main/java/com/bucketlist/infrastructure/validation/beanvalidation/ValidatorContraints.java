package com.bucketlist.infrastructure.validation.beanvalidation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorContraints {
	public static <T> void validate(T toValidate) {
		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		final Validator validator = factory.getValidator();
		final Set<ConstraintViolation<T>> violations = validator.validate(toValidate);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}
}
