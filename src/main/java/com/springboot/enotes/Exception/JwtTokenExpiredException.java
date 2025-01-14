package com.springboot.enotes.Exception;

public class JwtTokenExpiredException  extends RuntimeException{

	public JwtTokenExpiredException(String message) {
		super(message);
	}

	
}
