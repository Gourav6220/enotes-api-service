package com.springboot.enotes.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.springboot.enotes.Entity.Notes;
import com.springboot.enotes.Repository.NotesRespository;

@Component
public class NotesScheduler {

	@Autowired
	private NotesRespository noteRepo;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNotesScheduler() {
		LocalDateTime cutofdate=	LocalDateTime.now().minusDays(7);
		List<Notes> getallnotes=noteRepo.findByIsDeletedAndDeletedOnBefore(true,cutofdate);
			noteRepo.deleteAll(getallnotes);
		
	}
	
}
