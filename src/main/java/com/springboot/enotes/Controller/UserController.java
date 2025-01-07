package com.springboot.enotes.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Dto.UserResponse;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.util.CommonUtil;


@RestController
@RequestMapping("/api/vi/user")

public class UserController {

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(){
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse user=mapper.map(loggedInUser, UserResponse.class);
		
		return CommonUtil.createBuildResponse(user, HttpStatus.OK);
	}
	
	
	
}
