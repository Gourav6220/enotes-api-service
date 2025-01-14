package com.springboot.enotes.Exception;

public class JwtAuthorizationException extends RuntimeException {

	public JwtAuthorizationException(String message) {
		super(message);
	}

	
}
