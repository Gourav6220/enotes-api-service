package com.springboot.enotes.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.enotes.Dto.FavouriteNotesDto;
import com.springboot.enotes.Dto.NotesDto;
import com.springboot.enotes.Dto.NotesResponse;
import com.springboot.enotes.Endpoints.NotesControllerEndpoints;
import com.springboot.enotes.Entity.FileDetails;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Service.NotesSave;
import com.springboot.enotes.util.CommonUtil;

@RestController
public class NotesController implements NotesControllerEndpoints {

	@Autowired
	private NotesSave notesSave;
	

	@Override
public ResponseEntity<?> saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file) throws Exception{
	boolean savenotes=notesSave.saveNotes(notes,file);
		if(savenotes) {
			return CommonUtil.createBuildResponseMessage("Save Successfully", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@Override
public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		
		FileDetails filedtls=notesSave.getfiledetails(id);
		
		byte[] data=notesSave.downloadFile(filedtls);

		HttpHeaders headers=new HttpHeaders();
	
		String getfileextension=CommonUtil.getContenttype(filedtls.getOriginalFileName());
		
		headers.setContentType(MediaType.parseMediaType(getfileextension));
		headers.setContentDispositionFormData("attachment", filedtls.getOriginalFileName());
		
		return ResponseEntity.ok().headers(headers).body(data);
		
	}
	
	@Override
public ResponseEntity<?> getAllNotes(){
		
		List<NotesDto> notesDto=notesSave.getAllNotes();
		if(!CollectionUtils.isEmpty(notesDto)) {
			return CommonUtil.createBuildResponse(notesDto, HttpStatus.OK);
		}else {
			return  ResponseEntity.noContent().build();
}
		
	}

	
	@Override
public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo",defaultValue = "0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = "10") Integer pageSize
	)
	{
		
		NotesResponse notesDto=notesSave.getAllNotesByUser(pageNo,pageSize);
			return CommonUtil.createBuildResponse(notesDto, HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<?> getAllNotesBySearch(@RequestParam(name="key",defaultValue = "") String key,@RequestParam(name="pageNo",defaultValue = "0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = "10") Integer pageSize
			)
	{
		
		NotesResponse notesDto=notesSave.getAllNoteBySearch(pageNo,pageSize,key);
		return CommonUtil.createBuildResponse(notesDto, HttpStatus.OK);
	}
	
	@Override
public ResponseEntity<?> getDeleteNotesById(@PathVariable Integer id) throws Exception{
 notesSave.deleteNotesByid(id);
 return CommonUtil.createBuildResponseMessage("Notes Delete Successfully", HttpStatus.OK);
}



	@Override
public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
	notesSave.restoreNotes(id);
	return CommonUtil.createBuildResponseMessage("Notes restore Successfully", HttpStatus.OK);
}
	


	@Override
public ResponseEntity<?> getUserNotesRecycleBinNotes() throws Exception{

	List<NotesDto> notes=notesSave.getUserRecycleBinNotes();
if(CollectionUtils.isEmpty(notes)) {
	return CommonUtil.createBuildResponseMessage("Notes Not Found In Recycle bin", HttpStatus.OK);	
}
	return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
}


	@Override
public ResponseEntity<?> hardDeleteNotesById(@PathVariable Integer id) throws Exception{
 notesSave.hardDeleteNotesByid(id);
 return CommonUtil.createBuildResponseMessage("Notes Delete Successfully", HttpStatus.OK);
}



	@Override
public ResponseEntity<?> emptyRecycleBin() {
notesSave.userEmptyRecyclebin();
 return CommonUtil.createBuildResponseMessage("Notes Delete Successfully", HttpStatus.OK);
}



	@Override
public ResponseEntity<?> markfavouritesnotesbyuser(@PathVariable Integer notesId) throws Exception {
	notesSave.favouriteNotes(notesId);
	return CommonUtil.createBuildResponseMessage("Notes added favourite", HttpStatus.CREATED);
}



	@Override
public ResponseEntity<?> markUnfavouritesnotesbyuser(@PathVariable Integer favnotesId) throws Exception {
	notesSave.unFavouriteNotes(favnotesId);
	return CommonUtil.createBuildResponseMessage("Remove favourite", HttpStatus.OK);
}



	@Override
public ResponseEntity<?> getFavouritesnotesbyuser() {
List<FavouriteNotesDto> favnoteslist=	notesSave.GetUserFavouriteNotes();
if(!CollectionUtils.isEmpty(favnoteslist)) {
	 return CommonUtil.createBuildResponse(favnoteslist, HttpStatus.OK);
}
return CommonUtil.createBuildResponseMessage("Favourite Notes Not Found", HttpStatus.NO_CONTENT);

}


	@Override
public ResponseEntity<?> copynotesbyuser(@PathVariable Integer notesId) throws Exception {
boolean notescopysave=notesSave.copyNotes(notesId);
if(notescopysave) {
	return CommonUtil.createBuildResponseMessage("Notes Copied successfully", HttpStatus.CREATED);
}else {
	return CommonUtil.createBuildResponseMessage("Notes Copied Failed", HttpStatus.INTERNAL_SERVER_ERROR);
	
}

}



}
