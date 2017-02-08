package com.bucketlist.interfaces.shared;

import static java.util.stream.Collectors.toList;

import java.util.List;

public abstract class AbstractConverter<T, R, B> {

	public List<T> toEntity(final List<R> representations, final B builder) {
		return representations.stream()
				.map(r -> toEntity(r, builder))
				.collect(toList());
	}
	
	public List<R> toRepresentation(final List<T> entities) {
		return entities.stream()
				.map(this::toRepresentation)
				.collect(toList());
	}
	
	public abstract T toEntity(final R representation, final B builder);
	public abstract R toRepresentation(final T entity);
}
