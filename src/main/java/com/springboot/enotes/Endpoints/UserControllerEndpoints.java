package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.enotes.Dto.PasswordChangeRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User",description = "Authentication User Operation APIs")
@RequestMapping("/api/vi/user")
public interface UserControllerEndpoints {

	@Operation(summary = "User Get Profile",tags = {"User"},description = "User can get their profile.")
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	

	@Operation(summary = "User Account Password Change",tags = {"User"},description = "User can change their account password.")
	@PostMapping("/change-password")
	public ResponseEntity<?> savePasswordChange(@RequestBody PasswordChangeRequest passwordChangeRequest);
		
	
	
}
