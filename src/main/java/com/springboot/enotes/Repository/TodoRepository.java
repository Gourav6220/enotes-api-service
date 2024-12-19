package com.springboot.enotes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.enotes.Entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

	List<Todo> findAllByCreatedBy(int userid);

}
