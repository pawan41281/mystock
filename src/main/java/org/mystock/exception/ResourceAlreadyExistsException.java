package org.mystock.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -6173603573559317025L;
	private Object object;

	public ResourceAlreadyExistsException(String message, Object object) {
		super(message);
		this.object=object;
	}

	public Object getObject() {
		return object;
	}
}