package com.zkn.springmvc.util.exception;

public class CustomException extends RuntimeException{

	private static final long serialVersionUID = -1262092541892289333L;

	public CustomException() {
		super();
	}
	
	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable cause) {
		
		super(cause);
	}
	
	public CustomException(String message, Throwable cause) {

		super(message, cause);
	}

}