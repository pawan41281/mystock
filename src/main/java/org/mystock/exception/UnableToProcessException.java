package org.mystock.exception;

public class UnableToProcessException extends RuntimeException {
	
	private static final long serialVersionUID = 2121112633256225622L;

	public UnableToProcessException(String message) {
		super(message);
	}

}