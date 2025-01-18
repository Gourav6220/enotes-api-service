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

@RequestMapping("/api/vi/notes")
public interface NotesControllerEndpoints {

	@PostMapping("/save-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file) throws Exception;

	
	@GetMapping("/download/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
		
	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes();

	
	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo",defaultValue = "0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = "10") Integer pageSize
	);

	
	@GetMapping("/search")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllNotesBySearch(@RequestParam(name="key",defaultValue = "") String key,@RequestParam(name="pageNo",defaultValue = "0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = "10") Integer pageSize
			);

	
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getDeleteNotesById(@PathVariable Integer id) throws Exception;

	
	@GetMapping("/restore/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

	
	@GetMapping("/recycle-bin")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserNotesRecycleBinNotes() throws Exception;

	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> hardDeleteNotesById(@PathVariable Integer id) throws Exception;
	
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> emptyRecycleBin();


	@GetMapping("/fav/{notesId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> markfavouritesnotesbyuser(@PathVariable Integer notesId) throws Exception;


	@GetMapping("/un-fav/{favnotesId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> markUnfavouritesnotesbyuser(@PathVariable Integer favnotesId) throws Exception;

		
	@GetMapping("/fav-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getFavouritesnotesbyuser();

	
	@GetMapping("/copy-notes/{notesId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> copynotesbyuser(@PathVariable Integer notesId) throws Exception;

	
}
