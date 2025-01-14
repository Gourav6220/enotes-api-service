package com.springboot.enotes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.enotes.Entity.FavouriteNote;

public interface FavouriteNoteRepository  extends JpaRepository<FavouriteNote, Integer>{

	List<FavouriteNote> findByUserId(int userid);

}
