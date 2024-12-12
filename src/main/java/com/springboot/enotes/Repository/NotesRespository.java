package com.springboot.enotes.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.enotes.Entity.Notes;

public interface NotesRespository  extends JpaRepository<Notes,Integer>  {

	Page<Notes> findByCreatedBy(Integer userid, Pageable pageable);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userid);

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userid, Pageable pageable);

	List<Notes> findByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutofdate);

}
