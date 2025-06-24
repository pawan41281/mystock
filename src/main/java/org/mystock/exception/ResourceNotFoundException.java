package org.mystock.exception;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 2121112633256225622L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
