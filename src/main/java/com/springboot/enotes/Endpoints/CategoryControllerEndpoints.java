package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.springboot.enotes.util.Constants.ROLE_USER;
import static com.springboot.enotes.util.Constants.ROLE_ADMIN;
import static com.springboot.enotes.util.Constants.ROLE_ADMIN_USER;

import com.springboot.enotes.Dto.CategoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category",description = "All the Category Operation APIs")
@RequestMapping("/api/vi/category")
public interface CategoryControllerEndpoints {

	@Operation(summary   = "Save Category",tags = {"Category"},description = "Only Admin Save Catgeory")
	@PostMapping("/save-category")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category);
	
	
	@Operation(summary = "Get All Category",tags = {"Category"},description = "Only Admin Get Catgeory")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	
	@Operation(summary = "Get Active Category",tags = {"Category"},description = "Admin And User Get Active Catgeory")
	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getAllActiveCategory();

	@Operation(summary = "Get Category By id",tags = {"Category"},description = "Only Admin Get Catgeory Details by id")
	@GetMapping("{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategorybyid(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Delete Category",tags = {"Category"},description = "Only Admin Delete Catgeory Details by id")
	@DeleteMapping("{id}")
	@PreAuthorize(ROLE_ADMIN) 
	public ResponseEntity<?> getCategorydelete(@PathVariable Integer id);


	
}
