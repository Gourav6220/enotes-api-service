package com.springboot.enotes.Exception;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.enotes.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex){
		log.info("GlobalExceptionHandler: handleException() : {}",ex.getMessage());
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){
		log.info("GlobalExceptionHandler: handleAccessDeniedException() : {}",ex.getMessage());
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.FORBIDDEN);
	}
//	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception ex){
		log.info("GlobalExceptionHandler: handleNullPointerException() : {}",ex.getMessage());
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(ValidException.class)
	public ResponseEntity<?> handleValidException(ValidException ex){
		log.info("GlobalExceptionHandler: handleValidException() : {}",ex.getMessage());
		return CommonUtil.createErrorResponse(ex.getError(), HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception ex){
		log.info("GlobalExceptionHandler: handleResourceNotFoundException() : {}",ex.getMessage());
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex){
		log.info("GlobalExceptionHandler: handleIllegalArgumentException() : {}",ex.getMessage());
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException ex){
		log.info("GlobalExceptionHandler: handleExistDataException() : {}",ex.getMessage());
		return CommonUtil.createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex){
		log.info("GlobalExceptionHandler: handleFileNotFoundException() : {}",ex.getMessage());
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		List<ObjectError> getllerror=ex.getBindingResult().getAllErrors();

		Map<String,Object> error=new LinkedHashMap<>();
		getllerror.stream().forEach(er->{
			String msg=er.getDefaultMessage();
			String feild=((FieldError)(er)).getField(); 
			error.put(feild, msg);
		});
		
		
		log.info("GlobalExceptionHandler: handleMethodArgumentNotValidException() : {}",ex.getMessage());
		return CommonUtil.createErrorResponse(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(SuccessException ex){
		log.info("GlobalExceptionHandler: handleSuccessException() : {}",ex.getMessage());

		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex){
		log.info("GlobalExceptionHandler: handleBadCredentialsException() : {}",ex.getMessage());

		return CommonUtil.createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	


}
