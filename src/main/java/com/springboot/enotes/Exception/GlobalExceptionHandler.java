package com.springboot.enotes.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	


}
