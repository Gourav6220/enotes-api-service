package com.springboot.enotes.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.enotes.Dto.NotesDto;

public interface NotesSave {

	public boolean saveNotes(String notes,MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();
	
}
