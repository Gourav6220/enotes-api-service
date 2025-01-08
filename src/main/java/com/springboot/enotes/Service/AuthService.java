package com.springboot.enotes.Service;


import com.springboot.enotes.Dto.LoginRequest;
import com.springboot.enotes.Dto.LoginResponse;
import com.springboot.enotes.Dto.UserRequest;

public interface AuthService {

	public Boolean register(UserRequest userDto,String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);
	
	
}
