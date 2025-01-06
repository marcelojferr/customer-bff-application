package com.javaproject.customer.bff.exception;

public class CustomerException extends RuntimeException { 
	
	private static final long serialVersionUID = 1L;

	public CustomerException() {
		super();
	}
	public CustomerException(String errorMessage) {
		super(errorMessage);
	}
	
	public CustomerException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
