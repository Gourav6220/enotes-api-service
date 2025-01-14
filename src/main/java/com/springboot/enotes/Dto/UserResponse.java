package com.springboot.enotes.Dto;

import java.util.List;

import com.springboot.enotes.Dto.UserRequest.RoleDto;

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

public class UserResponse {

	
private Integer id;
	
	private String firstName;
	
	private String lastName;

	private String email;
	
	
	private String mobNo;

	private List<RoleDto> roles;

	private StatusDto status;
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
    @Builder
	public static class RoleDto {
		private int id;
		private String name;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
    @Builder
	public static class StatusDto {
		
		private Integer	id;

		private Boolean isActive;

	}

}

