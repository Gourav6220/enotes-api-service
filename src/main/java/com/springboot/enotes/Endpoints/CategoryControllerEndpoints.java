package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.enotes.Dto.CategoryDto;

@RequestMapping("/api/vi/category")
public interface CategoryControllerEndpoints {

	@PostMapping("/save-category")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category);
	
	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllCategory();
	
	
	@GetMapping("/active")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> getAllActiveCategory();


	@GetMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getCategorybyid(@PathVariable Integer id) throws Exception;
	
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getCategorydelete(@PathVariable Integer id);


	
}
