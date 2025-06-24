package org.mystock.exception;

public class ServiceNotRespondingException extends RuntimeException {
	
	private static final long serialVersionUID = 2121112633256225622L;

	public ServiceNotRespondingException(String message) {
		super(message);
	}

}