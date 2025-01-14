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

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex){
//		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){
//		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.FORBIDDEN);
	}
//	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception ex){
//		return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(ValidException.class)
	public ResponseEntity<?> handleValidException(ValidException ex){
//		return new ResponseEntity<>(ex.getError(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorResponse(ex.getError(), HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception ex){
//		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex){
//		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException ex){
//		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex){
//		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
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
		
		
//		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorResponse(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(SuccessException ex){
		return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex){
		return CommonUtil.createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	


}
