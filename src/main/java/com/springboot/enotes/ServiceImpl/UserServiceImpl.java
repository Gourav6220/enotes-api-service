package com.springboot.enotes.ServiceImpl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.springboot.enotes.Dto.EmailRequest;
import com.springboot.enotes.Dto.PasswordChangeRequest;
import com.springboot.enotes.Dto.PswdResetRequest;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Exception.ResourceNotFoundException;
import com.springboot.enotes.Repository.UserRepository;
import com.springboot.enotes.Service.UserService;
import com.springboot.enotes.util.CommonUtil;
import com.springboot.enotes.util.EmailService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailService emailService;

	
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

	@Override
	public void sendEmailPasswordReset(String email,HttpServletRequest Request) throws Exception {
		User user = userRepo.findByEmail(email);
	
	if(ObjectUtils.isEmpty(user)) {
		throw new ResourceNotFoundException("Invalid Emailid");
	}

	String passwordResettoken=UUID.randomUUID().toString();
	user.getStatus().setPasswordResetToken(passwordResettoken);
	User updateuser=userRepo.save(user);
	
	String url=CommonUtil.geturl(Request);
	
	sendEmailRequest(updateuser,url);
	
	}

	private void sendEmailRequest(User user,String url) throws Exception {
		
		String message="Hi,<b>[[username]]</b> "
				+ "<br> <p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>"
				+ "<p><a href='[[url]]'>Change my password</a></p>"
				+ "<p>Ignore this email if you do remember you password, or you have not made the request.</p><br>"
				+ "Thanks,<br>Mail Send By Gourav The Java developer";
		
		message=message.replace("[[username]]", user.getFirstName());
		message=message.replace("[[url]]", url+"/api/v1/home/verify-password-link?uid="+user.getId()+"&&code="+user.getStatus().getPasswordResetToken());
		
		
		EmailRequest emailRequest=EmailRequest
				.builder()
				.to(user.getEmail())
				.title("Password Reset")
				.subject("Password Reset Link")
				.message(message)
				.build();
		emailService.send(emailRequest);
	}

	@Override
	public Boolean verifyResetPassword(Integer uid, String code) throws Exception {

		User user = userRepo.findById(uid).orElseThrow(()-> new ResourceNotFoundException("Invalid User!!")) ;
		String passwordResetToken = user.getStatus().getPasswordResetToken();
		verifyPasswordResetToken(passwordResetToken,code);
		return true;
		
	}

	private void verifyPasswordResetToken(String passwordResetToken, String reqToken) {

		if(StringUtils.hasText(reqToken)) {
			
			if(!StringUtils.hasText(passwordResetToken)) {
				throw new IllegalArgumentException("Alredy Password Reset");
			}
			if(!passwordResetToken.equals(reqToken)) {
				throw new IllegalArgumentException("Invalid url");
				
			}				
			
		}else {
			throw new IllegalArgumentException("Invalid token");
		}
		
		
		
	}

	@Override
	public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception {
		User user = userRepo.findById(pswdResetRequest.getUserid()).orElseThrow(()-> new ResourceNotFoundException("Invalid User!!")) ;
		String encodenewPassword = passwordEncoder.encode(pswdResetRequest.getNewPassword());
		user.setPassword(encodenewPassword);
		user.getStatus().setPasswordResetToken(null);
		userRepo.save(user);
		
	}

}
