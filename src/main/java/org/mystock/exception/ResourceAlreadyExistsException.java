package org.mystock.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -6173603573559317025L;
	
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}

}
