package com.springboot.enotes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Dto.UserDto;
import com.springboot.enotes.Service.UserService;
import com.springboot.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/vi/user")

public class UserController {

	@Autowired
	private UserService userService;

	
	@PostMapping("/register-user")
	public ResponseEntity<?> saveUserDetails(@RequestBody UserDto userDto,HttpServletRequest request) throws Exception{
String url=CommonUtil.geturl(request);
		Boolean usersaveornot=userService.register(userDto,url);
		if(usersaveornot) {
			return CommonUtil.createBuildResponseMessage("User Register Successfully", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("User Registeration Failed!!!", HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
	
	
}
