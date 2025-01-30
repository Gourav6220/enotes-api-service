package com.springboot.enotes.Endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.springboot.enotes.util.Constants.ROLE_USER;
import static com.springboot.enotes.util.Constants.ROLE_ADMIN;
import static com.springboot.enotes.util.Constants.ROLE_ADMIN_USER;
import static com.springboot.enotes.util.Constants.DEFAULT_PAGE_NO;
import static com.springboot.enotes.util.Constants.DEFAULT_PAGE_SIZE;


@RequestMapping("/api/vi/notes")
public interface NotesControllerEndpoints {

	@PostMapping("/save-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file) throws Exception;

	
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
		
	
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes();

	
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo",defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
	);

	
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesBySearch(@RequestParam(name="key",defaultValue = "") String key,@RequestParam(name="pageNo",defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
			);

	
	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getDeleteNotesById(@PathVariable Integer id) throws Exception;

	
	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

	
	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserNotesRecycleBinNotes() throws Exception;

	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotesById(@PathVariable Integer id) throws Exception;
	
	
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyRecycleBin();


	@GetMapping("/fav/{notesId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> markfavouritesnotesbyuser(@PathVariable Integer notesId) throws Exception;


	@GetMapping("/un-fav/{favnotesId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> markUnfavouritesnotesbyuser(@PathVariable Integer favnotesId) throws Exception;

		
	@GetMapping("/fav-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getFavouritesnotesbyuser();

	
	@GetMapping("/copy-notes/{notesId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copynotesbyuser(@PathVariable Integer notesId) throws Exception;

	
}
