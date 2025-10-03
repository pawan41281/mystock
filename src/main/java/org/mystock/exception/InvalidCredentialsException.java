package org.mystock.exception;

public class InvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 5477367389066586951L;

	public InvalidCredentialsException(String message) {
		super(message);
	}

}
