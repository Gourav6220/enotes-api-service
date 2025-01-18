package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.enotes.Dto.PasswordChangeRequest;

@RequestMapping("/api/vi/user")
public interface UserControllerEndpoints {

	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	

	@PostMapping("/change-password")
	public ResponseEntity<?> savePasswordChange(@RequestBody PasswordChangeRequest passwordChangeRequest);
		
	
	
}
