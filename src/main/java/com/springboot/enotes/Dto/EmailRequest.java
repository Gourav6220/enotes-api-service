package com.springboot.enotes.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailRequest {

	private String to;
	private String title;
	private String subject;
	private String message;
	
	
}
