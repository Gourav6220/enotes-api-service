package com.springboot.enotes.ServiceImpl;


import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springboot.enotes.Config.Security.CustomUserDetails;
import com.springboot.enotes.Dto.EmailRequest;
import com.springboot.enotes.Dto.LoginRequest;
import com.springboot.enotes.Dto.LoginResponse;
import com.springboot.enotes.Dto.UserDto;
import com.springboot.enotes.Entity.AccountStatus;
import com.springboot.enotes.Entity.Role;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Repository.RoleRepository;
import com.springboot.enotes.Repository.UserRepository;
import com.springboot.enotes.Service.JwtService;
import com.springboot.enotes.Service.UserService;
import com.springboot.enotes.util.CustomValidation;
import com.springboot.enotes.util.EmailService;

import jakarta.servlet.http.HttpServletRequest;

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
	
	@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtToken;
	
	
	@Override
	public Boolean register(UserDto userDto,String url) throws Exception {

	validation.userValidation(userDto);
		
	User user=mapper.map(userDto, User.class);
	
	setRole(userDto,user);
	AccountStatus acstatus=AccountStatus.builder().isActive(false).verificationCode(UUID.randomUUID().toString()).build();
	user.setStatus(acstatus);
	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	User Saveuser=userRepo.save(user);
	if(!ObjectUtils.isEmpty(Saveuser)) {
sendemail(Saveuser,url);
		return true;
	}
	return false;
	
	
	}


	private void sendemail(User saveuser,String url) throws Exception {
		// TODO Auto-generated method stub
		
		String message="Hi,<b>[[username]]</b> "
				+ "<br> Your account register successfully.<br>"
				+ "<br> Click the below link verify your account <br>"
				+ "<a href='[[url]]'>Click Here</a><br><br>"
				+ "Thanks,<br>Mail Send By Gourav The Java developer";
		
		message=message.replace("[[username]]", saveuser.getFirstName());
		message=message.replace("[[url]]", url+"/api/v1/home/verify?uid="+saveuser.getId()+"&&code="+saveuser.getStatus().getVerificationCode());
		
		
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


	@Override
	public LoginResponse login(LoginRequest loginRequest) {
 
		  Authentication authenticate = authenticationManager.authenticate
				  (new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
	
		  if(authenticate.isAuthenticated()) {
			  CustomUserDetails userDetail=(CustomUserDetails) authenticate.getPrincipal();

String token=jwtToken.generateToken(userDetail.getUser());

LoginResponse loginres=LoginResponse.builder()
						.user(mapper.map(userDetail.getUser(), UserDto.class))
						.token(token)
						.build();
return loginres;
		  }
		  return null;
	}

}
