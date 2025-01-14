package com.springboot.enotes.Exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValidException  extends RuntimeException{

	private Map<String,Object> error;

	public ValidException(Map<String, Object> error) {
		super("Validation falied");
		this.error = error;
	}

	public Map<String, Object> getError() {
		return error;
	}

	
	
}
