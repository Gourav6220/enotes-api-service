package com.springboot.enotes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Dto.LoginRequest;
import com.springboot.enotes.Dto.LoginResponse;
import com.springboot.enotes.Dto.UserRequest;
import com.springboot.enotes.Endpoints.AuthControllerEndpoints;
import com.springboot.enotes.Service.AuthService;
import com.springboot.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController implements  AuthControllerEndpoints {

	@Autowired
	private AuthService authService;

	@Override
	public ResponseEntity<?> saveUserDetails(UserRequest userDto,HttpServletRequest request) throws Exception{
		 log.info("AuthController : saveUserDetails : Exceution Start");
				String url=CommonUtil.geturl(request);
				Boolean usersaveornot=authService.register(userDto,url);
				if(usersaveornot) {
					 log.info("Message : {} ","User Successfully Register ");
					return CommonUtil.createBuildResponseMessage("User Register Successfully", HttpStatus.CREATED);
				}
				 log.info("Message : {} ","User Registeration Failed!");
				return CommonUtil.createErrorResponseMessage("User Registeration Failed!!!", HttpStatus.INTERNAL_SERVER_ERROR);
					
			}
	
	@Override
public ResponseEntity<?> login(LoginRequest loginRequest){
			LoginResponse loginres=	authService.login(loginRequest);
				if(!ObjectUtils.isEmpty(loginres)) {
					return CommonUtil.createBuildResponse(loginres, HttpStatus.OK);
				}
				return CommonUtil.createErrorResponseMessage("Invalid Credenatials", HttpStatus.BAD_REQUEST);
				
			}
			

	
	
	
}
