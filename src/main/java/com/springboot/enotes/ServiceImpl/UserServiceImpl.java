package com.springboot.enotes.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.enotes.Dto.PasswordChangeRequest;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Repository.UserRepository;
import com.springboot.enotes.Service.UserService;
import com.springboot.enotes.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Boolean changePassword(PasswordChangeRequest passwordChangeRequest) {

	User loggedInUser = CommonUtil.getLoggedInUser();
	if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(),loggedInUser.getPassword())) {
		throw new IllegalArgumentException("Old password doesn't match!!!");
	}else {
		loggedInUser.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
		userRepo.save(loggedInUser);
		return true;
	}
	
	}

}
