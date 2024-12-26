package com.springboot.enotes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Service.HomeService;
import com.springboot.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/home")
 
public class HomeController {

	@Autowired
	private HomeService homeService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifiedNewuser(@RequestParam Integer uid,@RequestParam String code) throws Exception{

		Boolean verify=homeService.verifyAccount(uid, code);
		if(verify) {
			return CommonUtil.createBuildResponseMessage("Account Verification Success", HttpStatus.OK);
		}
		
return CommonUtil.createErrorResponseMessage("Invalid Verification", HttpStatus.BAD_REQUEST);		
	}
	
}
