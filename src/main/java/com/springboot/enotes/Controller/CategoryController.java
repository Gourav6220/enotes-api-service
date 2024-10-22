package com.springboot.enotes.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.enotes.Entity.Category;
import com.springboot.enotes.Service.CategorySave;

@Controller
@RequestMapping("/api/vi/category")
public class CategoryController {

	@Autowired
	private CategorySave categorySave;
	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody Category category){
	Boolean saveCategory=categorySave.saveCategory(category);
	if(saveCategory) {
		return new ResponseEntity<>("saved",HttpStatus.CREATED);
			
	}else {
		return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	}
	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory(){
		
		List<Category> allcategory=categorySave.getAllCategory();
	if(CollectionUtils.isEmpty(allcategory)) {
		return  ResponseEntity.noContent().build();
	}else {
		return new ResponseEntity<>(allcategory,HttpStatus.OK);
	}
	
	}
	
	
}
