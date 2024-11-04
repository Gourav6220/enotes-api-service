package com.springboot.enotes.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Dto.CategoryResponse;
import com.springboot.enotes.Entity.Category;
import com.springboot.enotes.Exception.ResourceNotFoundException;
import com.springboot.enotes.Service.CategorySave;
import com.springboot.enotes.util.CommonUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vi/category")
public class CategoryController {

	@Autowired
	private CategorySave categorySave;
	
	@PostMapping("/save-category")
//	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto category){
		public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category){
	Boolean saveCategory=categorySave.saveCategory(category);
	if(saveCategory) {
		return CommonUtil.createBuildResponseMessage("SuccessFully Saved", HttpStatus.CREATED);
		//return new ResponseEntity<>("Successfully Saved",HttpStatus.CREATED);
			
	}else {
		//return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	}
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory(){
		
		List<CategoryDto> allcategory=categorySave.getAllCategory();
	if(CollectionUtils.isEmpty(allcategory)) {
		return  ResponseEntity.noContent().build();
	}else {
//		return new ResponseEntity<>(allcategory,HttpStatus.OK);
		return CommonUtil.createBuildResponse(allcategory, HttpStatus.OK);
		}
	
	}
	@GetMapping("/active")
	public ResponseEntity<?> getAllActiveCategory(){
		
		List<CategoryResponse> allcategory=categorySave.getActiveCategory();

		if(CollectionUtils.isEmpty(allcategory)) {
			return  ResponseEntity.noContent().build();
		}else {
//			return new ResponseEntity<>(allcategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(allcategory, HttpStatus.OK);

		}
		
	}
	@GetMapping("{id}")
	public ResponseEntity<?> getCategorybyid(@PathVariable Integer id) throws Exception{
		CategoryDto categorydro=categorySave.getCategoryByid(id);
		
		if(ObjectUtils.isEmpty(categorydro)) {
//			return new ResponseEntity<>("Internal Server Error"+id,HttpStatus.NOT_FOUND);
			return CommonUtil.createErrorResponseMessage("Internal Server Error"+id, HttpStatus.NOT_FOUND);
		
		}else {
//			return new ResponseEntity<>(categorydro,HttpStatus.OK);
			return CommonUtil.createBuildResponse(categorydro, HttpStatus.OK);

		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> getCategorydelete(@PathVariable Integer id){
		Boolean categorydelete=categorySave.deleteCategoryByid(id);
		
		if(categorydelete) {
//			return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
			return CommonUtil.createBuildResponseMessage("Deleted Successfully", HttpStatus.OK);

		}else {
//			return new ResponseEntity<>("category not found with id: "+id,HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponseMessage("category not found with id: "+id, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	
	
}
