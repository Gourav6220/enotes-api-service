package com.springboot.enotes.Dto;

import java.util.Date;

import com.springboot.enotes.Entity.Category;
import com.springboot.enotes.Entity.FileDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotesDto {

	private Integer id;
	
	private String title;
	
	private String description;
	
	private CategoryDto category;

	private Integer createdBy;
	
	private Date createdOn; 
	
	private Integer updatedBy;
	
	private Date updatedOn;
	
	private FileDto fileDetails;
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter

	public static class CategoryDto{
		private Integer id;
		private String name;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter

	public static class FileDto{

		private Integer id;
		private String originalFileName;
		private String displayFileName;
		
		
	}
}
