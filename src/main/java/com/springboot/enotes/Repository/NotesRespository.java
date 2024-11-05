package com.springboot.enotes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.enotes.Entity.Notes;

public interface NotesRespository  extends JpaRepository<Notes,Integer>  {

}
