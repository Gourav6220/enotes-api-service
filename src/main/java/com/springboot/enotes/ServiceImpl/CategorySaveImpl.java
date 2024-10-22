package com.springboot.enotes.ServiceImpl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Dto.CategoryResponse;
import com.springboot.enotes.Entity.Category;
import com.springboot.enotes.Repository.CategoryRepository;
import com.springboot.enotes.Service.CategorySave;

@Service

public class CategorySaveImpl implements CategorySave {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public boolean saveCategory(CategoryDto categoryDto) {

//		Category category= new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
		Category category=mapper.map(categoryDto, Category.class);
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category c=categoryRepository.save(category);
if(ObjectUtils.isEmpty(c)) {
	return false;
	
}
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
	
		List<Category> allcategory=categoryRepository.findAll();
	
		List<CategoryDto> categoryDtoList = allcategory.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
		
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {

		List<Category> allcategory=categoryRepository.findByIsActiveTrue();
		List<CategoryResponse> categorylist = allcategory.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		
		return categorylist;
	}

}
