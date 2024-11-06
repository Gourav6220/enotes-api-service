package com.springboot.enotes.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.enotes.Entity.FileDetails;
import com.springboot.enotes.Handler.GenericResponse;

public class CommonUtil {

	public static ResponseEntity<?> createBuildResponse(Object data,HttpStatus status){
	
		GenericResponse response=GenericResponse.builder()
				.responseStatus(status)
				.status("Success")
				.message("Success")
				.data(data)
				.build();
return response.create();		
		
	}
	public static ResponseEntity<?> createBuildResponseMessage(String message,HttpStatus status){
		
		GenericResponse response=GenericResponse.builder()
				.responseStatus(status)
				.status("Success")
				.message(message)
				.build();
		return response.create();		
		
	}

	public static ResponseEntity<?> createErrorResponse(Object data,HttpStatus status){
		
		GenericResponse response=GenericResponse.builder()
				.responseStatus(status)
				.status("Failed")
				.message("failed")
				.data(data)
				.build();
return response.create();		
		
	}
	public static ResponseEntity<?> createErrorResponseMessage(String message,HttpStatus status){
		
		GenericResponse response=GenericResponse.builder()
				.responseStatus(status)
				.status("Failed")
				.message(message)
				.build();
		return response.create();		
		
	}
	
	public static String getContenttype(String  fileoriginalname) {
		String extension=FilenameUtils.getExtension(fileoriginalname);
		
		switch (extension) {
		case "pdf": 
		return "application/pdf";
		case "xlxs": 
			return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
		case "txt": 
			return "text/plan";
		case "png": 
			return "image/png";
		case "jpeg": 
			return "image/jpeg";
		case "jpg": 
			return "image/jpg";
		default:
			return "application/octet-stream";
		}
		
		
	}
	
}
