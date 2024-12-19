package com.springboot.enotes.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.enotes.Dto.TodoDto;
import com.springboot.enotes.Service.TodoService;
import com.springboot.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/vi/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@PostMapping("/save-todo")
	public ResponseEntity<?> saveTododata(@RequestBody TodoDto todo) throws Exception{
		
		boolean savetodo=todoService.saveTodo(todo);
		if(savetodo) {
		return 	CommonUtil.createBuildResponseMessage("Todo Saved Successfully", HttpStatus.CREATED);
		}
		return 	CommonUtil.createErrorResponseMessage("Todo Saved Failed!!!", HttpStatus.INTERNAL_SERVER_ERROR);
}
	
	
	@GetMapping("/")
	public ResponseEntity<?> getAlltododata(){
		
		List<TodoDto> getalltodolist=todoService.getTodoByUser();
	if(!CollectionUtils.isEmpty(getalltodolist)) {
		return CommonUtil.createBuildResponse(getalltodolist, HttpStatus.OK);
	}
	return  ResponseEntity.noContent().build();
	}
	

	@GetMapping("/{todoid}")
	public ResponseEntity<?> getTodoByid(@PathVariable Integer todoid) throws Exception{
		TodoDto gettododetail=todoService.getTodoByid(todoid);
		if(!ObjectUtils.isEmpty(gettododetail)) {
			return CommonUtil.createBuildResponse(gettododetail, HttpStatus.OK);
		}
		return  ResponseEntity.noContent().build();
		
	}
	
	
}
