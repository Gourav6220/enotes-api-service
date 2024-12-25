package com.springboot.enotes.ServiceImpl;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springboot.enotes.Dto.EmailRequest;
import com.springboot.enotes.Dto.UserDto;
import com.springboot.enotes.Entity.Role;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Repository.RoleRepository;
import com.springboot.enotes.Repository.UserRepository;
import com.springboot.enotes.Service.UserService;
import com.springboot.enotes.util.CustomValidation;
import com.springboot.enotes.util.EmailService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper mapper;
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private CustomValidation validation; 
	

	@Override
	public Boolean register(UserDto userDto) throws Exception {

	validation.userValidation(userDto);
		
	User user=mapper.map(userDto, User.class);
	
	setRole(userDto,user);
	User Saveuser=userRepo.save(user);
	if(!ObjectUtils.isEmpty(Saveuser)) {
sendemail(Saveuser);
		return true;
	}
	return false;
	
	
	}


	private void sendemail(User saveuser) throws Exception {
		// TODO Auto-generated method stub
		
		String message="Hi,<b>"+saveuser.getFirstName()+"</b> "
				+ "<br> Your account register successfully.<br>"
				+ "<br> Click the below link verify your account <br>"
				+ "<a href='#'>Click Here</a><br><br>"
				+ "Thanks,<br>Mail Send By Gourav The Java developer";
		
		EmailRequest emailRequest=EmailRequest
				.builder()
				.to(saveuser.getEmail())
				.title("Account Creating Confirmation")
				.subject("Account Created Success")
				.message(message)
				.build();
		emailService.send(emailRequest);
	}


	private void setRole(UserDto userDto, User user) {
		
	List<Integer> reqRoleid= userDto.getRoles().stream().map(r->r.getId()).toList();	
	List<Role> roles=roleRepo.findAllById(reqRoleid);
	user.setRoles(roles);
	
	
		
	}

}
