package com.bucketlist.domain.shared.specification;

public class InvalidSpecificationException extends RuntimeException {

	private static final long serialVersionUID = -3979297570465760516L;

	public InvalidSpecificationException() {}
	
	public InvalidSpecificationException(final String message) {
		super(message);
	}

}
