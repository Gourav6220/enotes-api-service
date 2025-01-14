package com.springboot.enotes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.enotes.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);


}
