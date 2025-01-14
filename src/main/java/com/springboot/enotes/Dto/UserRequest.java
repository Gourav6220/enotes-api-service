package com.springboot.enotes.Dto;

import java.util.List;

import com.springboot.enotes.Entity.Role;
import com.springboot.enotes.Entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
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

public class UserRequest {

	private Integer id;
	
	private String firstName;
	
	private String lastName;

	private String email;
	
	private String password;
	
	private String mobNo;

	private List<RoleDto> roles;

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
    @Builder
	public static class RoleDto {
		
		private int id;
		private String name;
	}
	
}
