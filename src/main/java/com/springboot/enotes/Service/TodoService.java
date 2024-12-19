package com.springboot.enotes.Service;

import java.util.List;

import com.springboot.enotes.Dto.TodoDto;

public interface TodoService {

	public boolean saveTodo(TodoDto todo) throws Exception;
	
	public TodoDto getTodoByid(Integer id) throws Exception;
	
	public List<TodoDto> getTodoByUser();
	
}
