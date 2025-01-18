package com.springboot.enotes.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Dto.CategoryResponse;
import com.springboot.enotes.Endpoints.CategoryControllerEndpoints;
import com.springboot.enotes.Service.CategorySave;
import com.springboot.enotes.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CategoryController implements CategoryControllerEndpoints{

	@Autowired
	private CategorySave categorySave;
	

	//	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto category){
	@Override
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


	@Override
	public ResponseEntity<?> getAllCategory(){
log.info("CategoryController: getAllCategory(): {}","Exceution Start");
		List<CategoryDto> allcategory=categorySave.getAllCategory();
	if(CollectionUtils.isEmpty(allcategory)) {
		log.info("CategoryController: getAllCategory(): {}","Exceution End");
		return  ResponseEntity.noContent().build();
	}else {
		log.info("CategoryController: getAllCategory(): {}","Exceution End");
		return CommonUtil.createBuildResponse(allcategory, HttpStatus.OK);
		}
	
	}

	
	@Override
	public ResponseEntity<?> getAllActiveCategory(){
		log.info("CategoryController: getAllActiveCategory(): {}","Exceution Start");

		List<CategoryResponse> allcategory=categorySave.getActiveCategory();

		if(CollectionUtils.isEmpty(allcategory)) {
			return  ResponseEntity.noContent().build();
		}else {
			log.info("CategoryController: getAllActiveCategory(): {}","Exceution End");
       return CommonUtil.createBuildResponse(allcategory, HttpStatus.OK);

		}
		
	}

	
	
	@Override
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
	
	
	
	@Override
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
