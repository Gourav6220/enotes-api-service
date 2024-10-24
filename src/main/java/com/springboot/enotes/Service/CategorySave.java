package com.springboot.enotes.Service;

import java.util.*;

import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Dto.CategoryResponse;
import com.springboot.enotes.Exception.ResourceNotFoundException;

public interface CategorySave {

	public boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory();

	public CategoryDto  getCategoryByid(Integer id) throws Exception;

	public Boolean deleteCategoryByid(Integer id);
	
}
