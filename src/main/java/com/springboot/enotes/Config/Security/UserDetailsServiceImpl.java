package com.springboot.enotes.Config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Repository.UserRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	User user=userRepo.findByEmail(username);
	
	if(user==null) {
		throw new UsernameNotFoundException("Invalid Email!!!!");
	}
	
	return new CustomUserDetails(user);
	
	}

	

}
