package com.springboot.enotes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Dto.PswdResetRequest;
import com.springboot.enotes.Endpoints.HomeControllerEndpoints;
import com.springboot.enotes.Service.HomeService;
import com.springboot.enotes.Service.UserService;
import com.springboot.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController implements HomeControllerEndpoints {

	@Autowired
	private HomeService homeService;

	
	@Autowired
	private UserService userService;
	
	
	@Override
public ResponseEntity<?> verifiedNewuser(Integer uid,String code) throws Exception{

		Boolean verify=homeService.verifyAccount(uid, code);
		if(verify) {
			return CommonUtil.createBuildResponseMessage("Account Verification Success", HttpStatus.OK);
		}
		
return CommonUtil.createErrorResponseMessage("Invalid Verification", HttpStatus.BAD_REQUEST);		
	}
	
	
	@Override
public ResponseEntity<?> sendEmailPasswordReset(String email,HttpServletRequest Request) throws Exception {
		userService.sendEmailPasswordReset(email,Request);
return CommonUtil.createBuildResponseMessage("Email Send Success!! Check Email Reset Password", HttpStatus.OK);
	}


	@Override
	public ResponseEntity<?> verifyPasswordResetLink(Integer uid,String code) throws Exception{
		
		Boolean verify=userService.verifyResetPassword(uid,code);
		if(verify) {
			return CommonUtil.createBuildResponseMessage("Password Reset Verification Success", HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage("Invalid Verification", HttpStatus.BAD_REQUEST);
	}

	@Override
public ResponseEntity<?> resetPassword(PswdResetRequest pswdResetRequest) throws Exception{
		userService.resetPassword(pswdResetRequest);
		return CommonUtil.createBuildResponseMessage("Password Reset Success", HttpStatus.OK);
	}
	
	
	
}
