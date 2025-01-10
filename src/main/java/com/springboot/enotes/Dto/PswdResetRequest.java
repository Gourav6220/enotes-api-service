package com.springboot.enotes.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PswdResetRequest {

	private Integer userid;
	
	private String newPassword;
	
	
}
