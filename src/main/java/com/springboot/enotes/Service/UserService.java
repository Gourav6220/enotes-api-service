package com.springboot.enotes.Service;

import com.springboot.enotes.Dto.PasswordChangeRequest;
import com.springboot.enotes.Dto.PswdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public Boolean changePassword(PasswordChangeRequest passwordChangeRequest);

	public void sendEmailPasswordReset(String email,HttpServletRequest request) throws Exception;

	public Boolean verifyResetPassword(Integer uid, String passwordresettoken) throws Exception;

	public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception;

	
	
}
