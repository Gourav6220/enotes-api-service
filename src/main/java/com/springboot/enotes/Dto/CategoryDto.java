package com.springboot.enotes.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDto {

	private Integer id;
	
	private String name;
	
	private String description;
	
	private Boolean isActive;
	
	private Boolean isDeleted;
	
	private Integer createdBy;
	
	private Date createdOn; 
	
	private Integer updatedBy;
	
	private Date updatedOn;
	
	
	
	
}

