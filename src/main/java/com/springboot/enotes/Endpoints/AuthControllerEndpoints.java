package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.enotes.Dto.LoginRequest;
import com.springboot.enotes.Dto.UserRequest;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/vi/auth")
public interface AuthControllerEndpoints {

	
	@PostMapping("/register-user")
	public ResponseEntity<?> saveUserDetails(@RequestBody UserRequest userDto,HttpServletRequest request) throws Exception;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);


}
