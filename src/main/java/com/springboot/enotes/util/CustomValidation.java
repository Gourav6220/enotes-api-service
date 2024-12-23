package com.springboot.enotes.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Dto.TodoDto;
import com.springboot.enotes.Dto.TodoDto.StatusDto;
import com.springboot.enotes.Dto.UserDto;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Enums.TodoStatus;
import com.springboot.enotes.Exception.ResourceNotFoundException;
import com.springboot.enotes.Exception.ValidException;
import com.springboot.enotes.Repository.RoleRepository;
import com.springboot.enotes.Repository.UserRepository;

import jakarta.validation.ValidationException;

@Component
public class CustomValidation {

@Autowired
private RoleRepository roleRepo;

@Autowired
private UserRepository userRepo;

	public void categoryValidation(CategoryDto categoryDto) {
		
		
		Map<String,Object> error=new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category JSON Can't be null");
		}else {
			
			if(ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("Name", "Name is not null");
			}else {
				
				if(categoryDto.getName().length()<3) {
					error.put("Name", "Name length should be minimum 3");
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
	
	public void todoValidation(TodoDto todo) throws Exception{
		StatusDto reqStatus=todo.getStatus();
		Boolean statusFound=false;
		for(TodoStatus st:TodoStatus.values()) {
			if(st.getId().equals(reqStatus.getId())) {
				statusFound=true;	
			}
		}
	
	if(!statusFound) {
		throw new ResourceNotFoundException("invalid status");
		
	}
	}
	
	public void userValidation(UserDto userdto) {
	
		Map<String,Object> error=new LinkedHashMap<>();

		if(!StringUtils.hasText(userdto.getFirstName())) {
			throw new IllegalArgumentException("first name is invalid!!!");
		}

		if(!StringUtils.hasText(userdto.getLastName())) {
			throw new IllegalArgumentException("last name is invalid!!!");
		}

		if(!StringUtils.hasText(userdto.getEmail()) || ! userdto.getEmail().matches(Constants.EMAIL_REGEX) ) {
			throw new IllegalArgumentException("email is invalid!!!");
		}
		if(!StringUtils.hasText(userdto.getMobNo()) || ! userdto.getMobNo().matches(Constants.MOBILE_REGEX) ) {
			throw new IllegalArgumentException("Mobile Number is invalid!!!");
		}
	
		if(CollectionUtils.isEmpty(userdto.getRoles()) ) {
			throw new IllegalArgumentException("Role is invalid!!!");
				
		}else {
			List<Integer> roleids= roleRepo.findAll().stream().map(r -> r.getId()).toList();
			
			List<Integer> invalidrolesid=userdto.getRoles().stream()
					.map(r -> r.getId())
					.filter(roleid->!roleids.contains(roleid)).toList();
			
			if(!CollectionUtils.isEmpty(invalidrolesid)) {
					throw new IllegalArgumentException("Role is invalid!!!"+invalidrolesid);
			}
	
		}
			}
	
	
	
	
}
