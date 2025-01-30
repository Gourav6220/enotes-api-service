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

import com.springboot.enotes.Dto.NotesRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.springboot.enotes.util.Constants.ROLE_USER;
import static com.springboot.enotes.util.Constants.ROLE_ADMIN;
import static com.springboot.enotes.util.Constants.ROLE_ADMIN_USER;
import static com.springboot.enotes.util.Constants.DEFAULT_PAGE_NO;
import static com.springboot.enotes.util.Constants.DEFAULT_PAGE_SIZE;


@Tag(name = "Notes",description = "All the Notes Operations APIs")
@RequestMapping("/api/vi/notes")
public interface NotesControllerEndpoints {

	@Operation(summary = "User Save Notes",tags = {"Notes","User"},description = "Only User can save notes with file")
	@PostMapping(value = "/save-notes",consumes = "multipart/form-data")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(@RequestParam
			@Parameter(description = "JSON String Notes",required = true
			,content = @Content(schema = @Schema(implementation = NotesRequest.class))) String  notes ,@RequestParam(required = false) MultipartFile file) throws Exception;

	
	@Operation(summary = "Download Notes",tags = {"Notes","User"},description = "User and Admin download the notes ")
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
		
	
	@Operation(summary = "Get All Notes",tags = {"Notes"},description = "Only Admin can get all the notes ")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes();

	
	@Operation(summary = "Get All Notes User",tags = {"Notes","User"},description = "Only User can get all the notes which they created.")
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo",defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
	);

	
	@Operation(summary = "Search Notes",tags = {"Notes","User"},description = "Only User can search notes which they created.")
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesBySearch(@RequestParam(name="key",defaultValue = "") String key,@RequestParam(name="pageNo",defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
			);

	
	@Operation(summary = "Soft Delete Notes",tags = {"Notes","User"},description = "Only User can delete notes which they created.")
	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getDeleteNotesById(@PathVariable Integer id) throws Exception;

	
	@Operation(summary = "Restore Notes",tags = {"Notes","User"},description = "Only User can restore notes which they deleted.")
	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

	
	@Operation(summary = "Get Notes From Recycle Bin",tags = {"Notes","User"},description = "Only User can get notes from recycle bin which they deleted.")
	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserNotesRecycleBinNotes() throws Exception;

	
	@Operation(summary = "Hard Delete Notes By id",tags = {"Notes","User"},description = "Only User can delete notes permanently by id.")
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotesById(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Hard Delete All Notes ",tags = {"Notes","User"},description = "Only User can delete all notes permanently.")
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyRecycleBin();


	@Operation(summary = "Add Notes In Favourite",tags = {"Notes","User"},description = "Only User can add notes in favourite.")
	@GetMapping("/fav/{notesId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> markfavouritesnotesbyuser(@PathVariable Integer notesId) throws Exception;


	@Operation(summary = "Remove Notes In Favourite",tags = {"Notes","User"},description = "Only User can remove notes in favourite.")
	@GetMapping("/un-fav/{favnotesId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> markUnfavouritesnotesbyuser(@PathVariable Integer favnotesId) throws Exception;

		
	@Operation(summary = "Get All Favourite Notes",tags = {"Notes","User"},description = "Only User can get all favourite notes.")
	@GetMapping("/fav-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getFavouritesnotesbyuser();

	
	@Operation(summary = "Copy Notes",tags = {"Notes","User"},description = "Only User can copy notes.")
	@GetMapping("/copy-notes/{notesId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copynotesbyuser(@PathVariable Integer notesId) throws Exception;

	
}
