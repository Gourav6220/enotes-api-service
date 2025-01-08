package com.springboot.enotes.Service;

import com.springboot.enotes.Dto.PasswordChangeRequest;

public interface UserService {

	public Boolean changePassword(PasswordChangeRequest passwordChangeRequest);
	
	
}
