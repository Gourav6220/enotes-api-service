package com.springboot.enotes.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.enotes.Entity.Notes;

public interface NotesRespository  extends JpaRepository<Notes,Integer>  {

	Page<Notes> findByCreatedBy(Integer userid, Pageable pageable);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userid);

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userid, Pageable pageable);

	List<Notes> findByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutofdate);

	
	@Query("select n from Notes n where (Lower(n.title) like lower(concat('%',:keyword,'%')) "
			+ " or Lower(n.description) like lower(concat('%',:keyword,'%')) "
			+ " or Lower(n.category.name) like lower(concat('%',:keyword,'%'))) "
			+ " and n.createdBy=:userid and n.isDeleted=false ")
	Page<Notes> searchNotes(@Param("keyword") String keyword,@Param("userid") Integer userid, Pageable pageable);

	
}
