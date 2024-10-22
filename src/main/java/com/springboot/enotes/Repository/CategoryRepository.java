package com.springboot.enotes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.enotes.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer > {

}
