package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.enotes.Dto.PswdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/home")
public interface HomeControllerEndpoints  {

	@GetMapping("/verify")
	public ResponseEntity<?> verifiedNewuser(@RequestParam Integer uid,@RequestParam String code) throws Exception;

	@GetMapping("/send-email-reset")
public ResponseEntity<?> sendEmailPasswordReset(@RequestParam String email,HttpServletRequest Request) throws Exception ;
	
	@GetMapping("/verify-password-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception;
		
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception;




	
	
}
