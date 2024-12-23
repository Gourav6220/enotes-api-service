package com.springboot.enotes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.enotes.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>  {

}
