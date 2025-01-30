package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.enotes.Dto.PswdResetRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Home",description = "All the Home APIs")
@RequestMapping("/api/v1/home")
public interface HomeControllerEndpoints  {

	@Operation(summary = "Verified New User",tags = {"Home"},description = "User Account verification after new registration")
	@GetMapping("/verify")
	public ResponseEntity<?> verifiedNewuser(@RequestParam Integer uid,@RequestParam String code) throws Exception;

	@Operation(summary = "Send Email Reset",tags = {"Home"},description = "Send Email For Password Reset")
	@GetMapping("/send-email-reset")
public ResponseEntity<?> sendEmailPasswordReset(@RequestParam String email,HttpServletRequest Request) throws Exception ;
	
	@Operation(summary = "Verified Password Reset Link",tags = {"Home"},description = "User Account verified after password reset")
	@GetMapping("/verify-password-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception;
		
	@Operation(summary = "Reset Password",tags = {"Home"},description = "For reset password")
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception;




	
	
}
