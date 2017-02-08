package com.bucketlist.domain.shared;

import java.util.Optional;

import com.bucketlist.domain.shared.specification.Validator;
import com.bucketlist.infrastructure.spring.context.ApplicationContextProvider;
import com.bucketlist.infrastructure.validation.beanvalidation.ValidatorContraints;

public abstract class AbstractBuilder<T> {

	protected T entity;

	public AbstractBuilder(final T entity) {
		this.entity = entity;
	}

	public final T build() {
		ValidatorContraints.validate(entity);
		validateSpecifications();
		final T entityBuilt = entity;
		this.entity = null;
		return entityBuilt;
	}

	private void validateSpecifications() {
		getValidatorClass().ifPresent(validatorClass -> {
		    final Validator<T> validatorSpecifications = ApplicationContextProvider.getApplicationContext()
		            .getBean(validatorClass);
		    validatorSpecifications.validate(entity);		    
		});
	}

	/**
	 * Override this method when the entity has specification to validate
	 * @return validator class
	 */
	protected Optional<Class<? extends Validator<T>>> getValidatorClass() {
		return Optional.empty();
	};
}
