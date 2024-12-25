package com.springboot.enotes.Service;


import com.springboot.enotes.Dto.UserDto;
public interface UserService {

	public Boolean register(UserDto userDto) throws Exception;
	
	
}
