package com.springboot.enotes.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Equals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Dto.CategoryResponse;
import com.springboot.enotes.Entity.Category;
import com.springboot.enotes.Exception.ExistDataException;
import com.springboot.enotes.Repository.CategoryRepository;
import com.springboot.enotes.ServiceImpl.CategorySaveImpl;
import com.springboot.enotes.util.CustomValidation;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepo;
	
	@InjectMocks
	private CategorySaveImpl categoryService;
	
	@Mock
	private CustomValidation validation;
	
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
	public void testSaveCategory() {
		// arrange
		when(categoryRepo.existsByName(categoryDto.getName())).thenReturn(false);
		when(mapper.map(categoryDto, Category.class)).thenReturn(category);
		when(categoryRepo.save(category)).thenReturn(category);
		
		// act
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		// assert
		assertTrue(saveCategory);
		
		// verify
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepo).existsByName(categoryDto.getName());
		verify(categoryRepo).save(category);
	}

	
	@Test
	public void testCategoryExist()
	{
		when(categoryRepo.existsByName(categoryDto.getName())).thenReturn(true);
		ExistDataException exception = assertThrows(ExistDataException.class, ()->{
			categoryService.saveCategory(categoryDto);
		});
		
		assertEquals("Category Name Exist In our database", exception.getMessage());
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepo).existsByName(categoryDto.getName());
		verify(categoryRepo,never()).save(category);
	}
	
	@Test
	public void testUpdateCategory() {
		categoryDto.setId(1);
		category.setId(1);
		
		// arrange
		when(categoryRepo.existsByName(categoryDto.getName())).thenReturn(false);
		when(mapper.map(categoryDto, Category.class)).thenReturn(category);
		when(categoryRepo.save(category)).thenReturn(category);
		
		// act
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		// assert
		assertTrue(saveCategory);
		
		// verify
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepo).existsByName(categoryDto.getName());
		verify(categoryRepo).save(category);
	}

	@Test
	public void getAllcategory() {
		
		when(categoryRepo.findByIsDeletedFalse()).thenReturn(categories);
		
		List<CategoryDto> allcategory=categoryService.getAllCategory();
		
		assertEquals(allcategory.size(), categories.size());
		
		verify(categoryRepo).findByIsDeletedFalse();
		
		
	}
	@Test
	public void getActivecategory() {
		
		when(categoryRepo.findByIsActiveTrueAndIsDeletedFalse()).thenReturn(categories);
		
		List<CategoryResponse> allcategory=categoryService.getActiveCategory();
		
		assertEquals(allcategory.size(), categories.size());
		
		verify(categoryRepo).findByIsActiveTrueAndIsDeletedFalse();
		
		
	}
	
	
}
