package com.springboot.enotes.Service;

import java.util.*;


import com.springboot.enotes.Entity.Category;

public interface CategorySave {

	public boolean saveCategory(Category category);
	
	public List<Category> getAllCategory();
	
}
