package com.springboot.enotes.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.enotes.Dto.NotesDto;
import com.springboot.enotes.Service.NotesSave;
import com.springboot.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/vi/notes")
public class NotesController {

	@Autowired
	private NotesSave notesSave;
	
	@PostMapping("/save-notes")
	public ResponseEntity<?> saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file) throws Exception{

		boolean savenotes=notesSave.saveNotes(notes,file);
		
		if(savenotes) {
			return CommonUtil.createBuildResponseMessage("Save Successfully", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes(){
		List<NotesDto> notesDto=notesSave.getAllNotes();
		if(!CollectionUtils.isEmpty(notesDto)) {
			return CommonUtil.createBuildResponse(notesDto, HttpStatus.OK);
		}else {
			return  ResponseEntity.noContent().build();
}
		
	}
	
	
	
}
