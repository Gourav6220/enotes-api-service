package com.springboot.enotes.Service;

import com.springboot.enotes.Entity.User;

public interface JwtService {

	public String generateToken(User user);
	
}
