package com.springboot.enotes.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Dto.PasswordChangeRequest;
import com.springboot.enotes.Dto.UserResponse;
import com.springboot.enotes.Endpoints.UserControllerEndpoints;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Service.UserService;
import com.springboot.enotes.util.CommonUtil;


@RestController
public class UserController implements UserControllerEndpoints {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ResponseEntity<?> getProfile(){
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse user=mapper.map(loggedInUser, UserResponse.class);
		
		return CommonUtil.createBuildResponse(user, HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<?> savePasswordChange(@RequestBody PasswordChangeRequest passwordChangeRequest){
		
		Boolean userPasswordChange=userService.changePassword(passwordChangeRequest) ;
		if(userPasswordChange) {
			return CommonUtil.createBuildResponseMessage("Password Change Successfully!!!", HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage("Password Change Failed!!!", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	
}
