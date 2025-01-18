package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.enotes.Dto.TodoDto;

@RequestMapping("/api/vi/todo")
public interface TodoControllerEndpoints {

	@PostMapping("/save-todo")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveTododata(@RequestBody TodoDto todo) throws Exception;
	
	
	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAlltododata();

	
	@GetMapping("/{todoid}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getTodoByid(@PathVariable Integer todoid) throws Exception;

	
}
