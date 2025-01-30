package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.enotes.Dto.TodoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import static com.springboot.enotes.util.Constants.ROLE_USER;
import static com.springboot.enotes.util.Constants.ROLE_ADMIN;
import static com.springboot.enotes.util.Constants.ROLE_ADMIN_USER;
import static com.springboot.enotes.util.Constants.DEFAULT_PAGE_NO;
import static com.springboot.enotes.util.Constants.DEFAULT_PAGE_SIZE;


@Tag(name = "Todo",description = "All the Todo Operations APIs")
@RequestMapping("/api/vi/todo")
public interface TodoControllerEndpoints {

	@Operation(summary = "User Save Todo",tags = {"Todo"},description = "Only User can save todo ")
	@PostMapping("/save-todo")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTododata(@RequestBody TodoDto todo) throws Exception;
	
	
	@Operation(summary = "User Get Todo",tags = {"Todo"},description = "Only User can get all todo ")
	@GetMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAlltododata();

	
	@Operation(summary = "User Get Todo by ID",tags = {"Todo"},description = "Only User can get todo by id")
	@GetMapping("/{todoid}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByid(@PathVariable Integer todoid) throws Exception;

	
}
