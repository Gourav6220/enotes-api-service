package com.springboot.enotes.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springboot.enotes.Entity.Category;
import com.springboot.enotes.Repository.CategoryRepository;
import com.springboot.enotes.Service.CategorySave;

@Service

public class CategorySaveImpl implements CategorySave {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public boolean saveCategory(Category category) {

		category.setIsDeleted(false);
		category.setCreatedBy(1);
		Category c=categoryRepository.save(category);
if(ObjectUtils.isEmpty(c)) {
	return false;
	
}
		return true;
	}

	@Override
	public List<Category> getAllCategory() {
	
		List<Category> allcategory=categoryRepository.findAll();
	
		return allcategory;
	}

}
