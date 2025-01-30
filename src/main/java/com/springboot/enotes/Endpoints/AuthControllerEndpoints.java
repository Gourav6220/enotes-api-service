package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.enotes.Dto.LoginRequest;
import com.springboot.enotes.Dto.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Authentication",description = "All the user Authentication APIs")
@RequestMapping("/api/vi/auth")
public interface AuthControllerEndpoints {

	
	@ApiResponses(value = {@ApiResponse(responseCode = "201",description = "Register Success"),
			@ApiResponse(responseCode = "500",description = "Internal Server error"),
		@ApiResponse(responseCode = "400",description = "Bad Request")	})
	@Operation(summary = "User Register",tags = {"Authentication","Home"})
	@PostMapping("/register-user")
	public ResponseEntity<?> saveUserDetails(@RequestBody UserRequest userDto,HttpServletRequest request) throws Exception;
	
	@Operation(summary = "User Login",tags = {"Authentication","Home"})
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);


}
