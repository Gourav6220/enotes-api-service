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

@RequestMapping("/api/vi/category")
public interface CategoryControllerEndpoints {

	@PostMapping("/save-category")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category);
	
	
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	
	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getAllActiveCategory();


	@GetMapping("{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategorybyid(@PathVariable Integer id) throws Exception;
	
	
	@DeleteMapping("{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategorydelete(@PathVariable Integer id);


	
}
