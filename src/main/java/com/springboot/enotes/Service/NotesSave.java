package com.springboot.enotes.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.enotes.Dto.FavouriteNotesDto;
import com.springboot.enotes.Dto.NotesDto;
import com.springboot.enotes.Dto.NotesResponse;
import com.springboot.enotes.Entity.FileDetails;
import com.springboot.enotes.Exception.ResourceNotFoundException;

public interface NotesSave {

	public boolean saveNotes(String notes,MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public FileDetails getfiledetails(Integer id) throws Exception ;

	public byte[] downloadFile(FileDetails filedtls) throws Exception;

	public NotesResponse getAllNotesByUser(Integer pageNo, Integer pageSize);

	public void deleteNotesByid(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDto> getUserRecycleBinNotes();

	public void hardDeleteNotesByid(Integer id) throws Exception;

	public void userEmptyRecyclebin(); 

	public void favouriteNotes(Integer notesid) throws Exception;
	
	public void unFavouriteNotes(Integer favouriteid) throws Exception;

	public List<FavouriteNotesDto> GetUserFavouriteNotes();

	public boolean copyNotes(Integer notesId) throws Exception;
	
}
