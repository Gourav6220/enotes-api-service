package com.springboot.enotes.Service;

import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.enotes.Entity.User;

public interface JwtService {

	public String generateToken(User user);

	public String extractUsername(String token);
	
	public Boolean validation(String token, UserDetails userDetails);
	
}
