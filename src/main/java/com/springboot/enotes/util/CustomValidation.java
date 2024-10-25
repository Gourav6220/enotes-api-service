package com.springboot.enotes.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Exception.ValidException;

import jakarta.validation.ValidationException;

@Component
public class CustomValidation {

	public void categoryValidation(CategoryDto categoryDto) {
		
		Map<String,Object> error=new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category JSON Can't be null");
		}else {
			
			if(ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("Name", "Name is not null");
			}else {
				
				if(categoryDto.getName().length()<10) {
					error.put("Name", "Name length should be minimum 10");
				}
				if(categoryDto.getName().length()>100) {
					error.put("Name", "Name length should maximun 100");
				}
				
			}
			
			if(ObjectUtils.isEmpty(categoryDto.getDescription())) {
				
					error.put("Description", "Description is not null");
				
			}
			
			if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("IsActive", "isActive is not fill null");
				
			}else {
				if(categoryDto.getIsActive() != Boolean.TRUE.booleanValue()
						&& categoryDto.getIsActive() != Boolean.FALSE.booleanValue() ) {
					
					error.put("IsActive", "Invalid value isActive Filed");
					
				}	
			}
			
			
			
		}
		
		if(!error.isEmpty()) {
			
			throw new ValidException(error); 
				
		}
		
		
	}
	
	
}
