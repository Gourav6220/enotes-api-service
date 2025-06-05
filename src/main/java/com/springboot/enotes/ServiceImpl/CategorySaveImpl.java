package com.springboot.enotes.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Dto.CategoryResponse;
import com.springboot.enotes.Entity.Category;
import com.springboot.enotes.Exception.ExistDataException;
import com.springboot.enotes.Exception.ResourceNotFoundException;
import com.springboot.enotes.Repository.CategoryRepository;
import com.springboot.enotes.Service.CategorySave;
import com.springboot.enotes.util.CustomValidation;

@Service

public class CategorySaveImpl implements CategorySave {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CustomValidation customValidation;
	
	@Override
	public boolean saveCategory(CategoryDto categoryDto) {

// validation checking
		customValidation.categoryValidation(categoryDto);
		
		boolean namefound=categoryRepository.existsByName(categoryDto.getName());

		if(namefound) {
			throw new ExistDataException("Category Name Exist In our database");
		}
		
		Category category=mapper.map(categoryDto, Category.class);
		
		if(ObjectUtils.isEmpty(category.getId())) {
				category.setIsDeleted(false);
				category.setCreatedOn(new Date());
		}else {
			updateCategory(category);
		}

		
		Category c=categoryRepository.save(category);
		if(ObjectUtils.isEmpty(c)) {
			return false;
			
		}
		return true;
	}

	private void updateCategory(Category category) {
		
	Optional<Category> findbyid=categoryRepository.findById(category.getId());
		if(findbyid.isPresent()) {
	Category	exitcategory=findbyid.get();	
	category.setCreatedBy(exitcategory.getCreatedBy());
	category.setCreatedOn(exitcategory.getCreatedOn());
	category.setIsDeleted(exitcategory.getIsDeleted());
//	category.setUpdatedBy(1);
//	category.setUpdatedOn(new Date());
		}
		
	}

	@Override
	public List<CategoryDto> getAllCategory() {
	
		List<Category> allcategory=categoryRepository.findByIsDeletedFalse();
	
		List<CategoryDto> categoryDtoList = allcategory.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
		
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {

		List<Category> allcategory=categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categorylist = allcategory.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		
		return categorylist;
	}

	@Override
	public CategoryDto getCategoryByid(Integer id) throws Exception {

	Category category=categoryRepository.findByIdAndIsDeletedFalse(id)
			.orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
	
	if(category.getName()==null) {
throw new IllegalArgumentException("name is null");		
	}
//	category.getName().substring(1);
	
	if(!ObjectUtils.isEmpty(category)) {
	return mapper.map(category, CategoryDto.class);
	}
	
	return null;
	
	}

	@Override
	public Boolean deleteCategoryByid(Integer id) {
		Optional<Category> categorbyid=categoryRepository.findById(id);
		
		if(categorbyid.isPresent()) {
		Category category=categorbyid.get();
		category.setIsDeleted(true);
		categoryRepository.save(category);
		return true;
		}
		
		return false;
		
		}
}	
