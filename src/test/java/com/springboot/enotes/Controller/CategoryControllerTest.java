package com.springboot.enotes.Controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Entity.Category;
import com.springboot.enotes.ServiceImpl.CategorySaveImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

	@Mock
	private CategorySaveImpl categoryService;

	@InjectMocks
	private CategoryController categoryController;

	@Mock
	private ModelMapper mapper;

	
	CategoryDto categoryDto=null;
	Category category=null;
	
	List<Category> categories=new ArrayList<>();
	List<CategoryDto> categoriesDto=new ArrayList<>();
	
	@BeforeEach
	public void intialize() {
		
		categoryDto=CategoryDto.builder()
				.id(null)
				.name("Java")
				.description("Java Notes")
				.isActive(true).build();
		
		category=mapper.map(categoryDto, Category.class);

		category=Category.builder()
				.id(null)
				.name("Java Notes")
				.description("java notes")
				.isActive(true)
				.isDeleted(false)
				.build();
		
		categories.add(category);
		categoriesDto.add(categoryDto);
		
		
	}

	@Test
	public void testSaveCategoryController() {
		when(categoryService.saveCategory(categoryDto)).thenReturn(true);
		ResponseEntity<?> response = categoryController.saveCategory(categoryDto);
		Object body=response.getBody();
		Map<String,String> json=(Map<String,String>)body;
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		Assertions.assertEquals(json.get("status"), "Success");
		
	}
	@Test
	public void testCategoryNotSavedController() {
		when(categoryService.saveCategory(categoryDto)).thenReturn(false);
		ResponseEntity<?> response = categoryController.saveCategory(categoryDto);
		Object body=response.getBody();
		Map<String,String> json=(Map<String,String>)body;
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
		Assertions.assertEquals(json.get("status"), "Failed");
		Assertions.assertEquals(json.get("message"), "Not Saved");
		
	}
	
}
