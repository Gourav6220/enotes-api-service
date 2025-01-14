package com.springboot.enotes.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.enotes.Entity.AccountStatus;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Exception.ResourceNotFoundException;
import com.springboot.enotes.Exception.SuccessException;
import com.springboot.enotes.Repository.UserRepository;
import com.springboot.enotes.Service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Boolean verifyAccount(Integer id, String vcode) throws Exception {
		
	User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid User id!! user not found") );
	if(user.getStatus().getVerificationCode()==null) {
		throw new SuccessException("Account already verified");
	}
	
		if(user.getStatus().getVerificationCode().equals(vcode)) {
			
			AccountStatus acstatus=AccountStatus
					.builder()
					.isActive(true)
					.verificationCode(null)
					.build();
			user.setStatus(acstatus);
			userRepo.save(user);
return true;
		}
		return false;
	}

}
