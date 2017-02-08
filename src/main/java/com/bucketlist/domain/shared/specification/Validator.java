package com.bucketlist.domain.shared.specification;

import java.util.List;

public abstract class Validator<T> {
		
	public void validate(final T entity) {
		specifications().forEach(spec -> {
			if(!spec.isSatisfiedBy(entity)) {
				throw new InvalidSpecificationException(spec.getMessageOnFail());
			}
		});
	}
	
	public abstract List<AbstractSpecification<T>> specifications();
}
