package com.springboot.enotes.ServiceImpl;

import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springboot.enotes.Dto.TodoDto;
import com.springboot.enotes.Dto.TodoDto.StatusDto;
import com.springboot.enotes.Entity.Notes;
import com.springboot.enotes.Entity.Todo;
import com.springboot.enotes.Enums.TodoStatus;
import com.springboot.enotes.Exception.ResourceNotFoundException;
import com.springboot.enotes.Repository.TodoRepository;
import com.springboot.enotes.Service.TodoService;
import com.springboot.enotes.util.CustomValidation;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CustomValidation validation;
	
	@Override
	public boolean saveTodo(TodoDto todo) throws Exception {

		validation.todoValidation(todo);
		Todo savetodo=mapper.map(todo, Todo.class);
		savetodo.setStatusId(todo.getStatus().getId());
		
		if(!ObjectUtils.isEmpty(savetodo)) {
				todoRepo.save(savetodo);
				return true;
			}
		return false;
	}

	@Override
	public TodoDto getTodoByid(Integer id) throws Exception{

		Todo getTodo=todoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo Id not valid !! Id not found"));

		TodoDto getTodoDto=mapper.map(getTodo, TodoDto.class);
		setStatus(getTodoDto,getTodo);

		return getTodoDto;
	}

	private void setStatus(TodoDto getTodoDto, Todo getTodo) {
	
		for(TodoStatus st:TodoStatus.values()) {
			if(st.getId().equals(getTodo.getStatusId())) {
				StatusDto statusDto=StatusDto.builder()
						.id(st.getId())
						.name(st.getName())
						.build();
				getTodoDto.setStatus(statusDto);
			}
		}
	}

	@Override
	public List<TodoDto> getTodoByUser() {
int userid=1;
	
	List<Todo> gettodolistuser=	todoRepo.findAllByCreatedBy(userid);	
	List<TodoDto> gettodoDtolist=gettodolistuser.stream().map(todo->mapper.map(todo, TodoDto.class)).toList();
	setStatus(gettodoDtolist);
	return gettodoDtolist;
	}

	private void setStatus(List<TodoDto> gettodoDtolist) {
	
		for(TodoDto todo:gettodoDtolist) {
			  StatusDto status = todo.getStatus(); // Assuming getStatus() returns StatusDto
		     
			  for(TodoStatus st:TodoStatus.values()) {
					if(st.getId().equals(status.getId())) {
						StatusDto statusDto=StatusDto.builder()
								.id(st.getId())
								.name(st.getName())
								.build();
						todo.setStatus(statusDto);
					}
			  }

			}
		
	}
	
}
