package io.s3soft.registration.exception;

import org.springframework.validation.BindingResult;

public class UserDataException extends RuntimeException{
	private BindingResult errors;
	private  static final long serialVersionUID=1L;
	public UserDataException(String message) {
		super(message);
	}
	public UserDataException(String message,BindingResult errors) {
		this(message);
		this.errors=errors;
	}
	public BindingResult getErrors() {
		return errors;
	}
	
}
