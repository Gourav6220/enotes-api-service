package com.springboot.enotes.ServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import javax.print.DocFlavor.INPUT_STREAM;

import org.apache.catalina.mapper.Mapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.enotes.Dto.NotesDto;
import com.springboot.enotes.Dto.NotesDto.CategoryDto;
import com.springboot.enotes.Dto.NotesDto.FileDto;
import com.springboot.enotes.Dto.NotesResponse;
import com.springboot.enotes.Entity.FavouriteNote;
import com.springboot.enotes.Entity.FileDetails;
import com.springboot.enotes.Entity.Notes;
import com.springboot.enotes.Exception.ResourceNotFoundException;
import com.springboot.enotes.Repository.CategoryRepository;
import com.springboot.enotes.Repository.FavouriteNoteRepository;
import com.springboot.enotes.Repository.FileRepository;
import com.springboot.enotes.Repository.NotesRespository;
import com.springboot.enotes.Dto.FavouriteNotesDto;
import com.springboot.enotes.Service.NotesSave;

@Service
public class NotesSaveImpl implements NotesSave {

	@Autowired
	private NotesRespository notesRespository;

	@Autowired
	private CategoryRepository categoryRespository;

	@Autowired
	private FavouriteNoteRepository favouriteNoteRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private FileRepository fileRespository;

	@Value("${file.upload.path}")
	private String uploadpath;
	
	@Override
	public boolean saveNotes(String notes,MultipartFile file) throws Exception {
	
		ObjectMapper ob=new ObjectMapper();
		NotesDto notesdto= ob.readValue(notes,NotesDto.class);
		notesdto.setIsDeleted(false);
		notesdto.setDeletedOn(null);
		
		if(!ObjectUtils.isEmpty(notesdto.getId())) {
			updateNotes(notesdto,file);
			
			
		}
		
		checkcategoryExist(notesdto.getCategory());

		Notes savenotes=mapper.map(notesdto, Notes.class);

		FileDetails filedlts=saveFileDetails(file);
		if(!ObjectUtils.isEmpty(filedlts)) {
			savenotes.setFileDetails(filedlts);
		}else {
			if(ObjectUtils.isEmpty(notesdto.getId())) {
				savenotes.setFileDetails(null);
						}			
		}
		
	Notes n=notesRespository.save(savenotes);
if(ObjectUtils.isEmpty(n)) {
	return false;
}
		return true;
	}

	private void updateNotes(NotesDto notesdto, MultipartFile file) throws ResourceNotFoundException {

		Notes notesupdate= notesRespository.findById(notesdto.getId()).orElseThrow(()-> new ResourceNotFoundException("Id is not valid"));

if(ObjectUtils.isEmpty(file)) {
	notesdto.setFileDetails(mapper.map(notesupdate.getFileDetails(), FileDto.class));
}
		
		
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
	
		if(!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
	
			String originalfilename=file.getOriginalFilename();
		List<String> allext=Arrays.asList("pdf","xlsx","jpg","docs","png");	
		String extension=FilenameUtils.getExtension(originalfilename);
		if(!allext.contains(extension)) {
			 throw new  IllegalArgumentException("invalid file format ! uppload only .pdf , .xlsx, .jpg, .docs, .png");
		}
		

			String setdisplayFileName=getdisplayname(originalfilename);
			
			String randomnumnber=UUID.randomUUID().toString();
			
			
			String uploadfilename=randomnumnber +"."+extension;

			File saveFile=new File(uploadpath);
			if(!saveFile.exists())
			{
				saveFile.mkdir();
			}
			
			String storepath=uploadpath.concat(uploadfilename);
			
			long upload=Files.copy(file.getInputStream(), Paths.get(storepath));
			if(upload!=0) {
				FileDetails filedts=new FileDetails();
				filedts.setOriginalFileName(originalfilename);
				filedts.setUploadFileName(uploadfilename);
				filedts.setDisplayFileName(setdisplayFileName);
				filedts.setPath(storepath);
				filedts.setFileSize(file.getSize());
			FileDetails fileSave=	fileRespository.save(filedts);
			
			return fileSave;
			}
		}
		
		return null;
	}

	private String getdisplayname(String originalfilename) {

		String extension=FilenameUtils.getExtension(originalfilename);
		String filename=FilenameUtils.removeExtension(originalfilename);
		if(filename.length()>8) {
			filename=filename.substring(0,7);
		}
		filename=filename +"."+ extension;
		return filename;
	
	
	}

	private void checkcategoryExist(CategoryDto category) throws Exception{
		categoryRespository.findById(category.getId())
		.orElseThrow(()->new ResourceNotFoundException("Category Id is not found"));
		
		
	}

	@Override
	public List<NotesDto> getAllNotes() {

		List<Notes> notes=notesRespository.findAll();
		List<NotesDto> dtonotes=notes.stream().map(not->mapper.map(not, NotesDto.class)).toList();
		
		return dtonotes;

	}

	
	public byte[] downloadFile(FileDetails filedtls) throws Exception {
		
		InputStream io=new FileInputStream(filedtls.getPath());
		
		return  StreamUtils.copyToByteArray(io);
	}
	
	
	@Override
	public FileDetails getfiledetails(Integer id) throws Exception {

	FileDetails filedtls=fileRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("File is not available"));
		
	return filedtls;
	}

	@Override
	public NotesResponse getAllNotesByUser(Integer userid,Integer pageNo, Integer pageSize) {
	
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		
		Page<Notes> pagenotes=notesRespository.findByCreatedByAndIsDeletedFalse(userid,pageable);
		
		List<NotesDto> notes=pagenotes.get().map(p->mapper.map(p, NotesDto.class)).toList();

		
		
		NotesResponse noteresponse=NotesResponse.builder()
				.notes(notes)
				.pageNo(pagenotes.getNumber())
				.pageSize(pagenotes.getSize())
				.totalElement(pagenotes.getTotalElements())
				.totalPages(pagenotes.getTotalPages())
				.isFirst(pagenotes.isFirst())
				.isLast(pagenotes.isLast())
				.build();
		
		return noteresponse;
	}

	@Override
	public void  deleteNotesByid(Integer id) throws Exception {
Notes getnotes=notesRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notes id not valid ! not found"));
	
		getnotes.setIsDeleted(true);
		getnotes.setDeletedOn(LocalDateTime.now());
		getnotes.setUpdatedOn(getnotes.getUpdatedOn());
		
		notesRespository.save(getnotes);
	}
	@Override
	public void  restoreNotes(Integer id) throws Exception {
		Notes getnotes=notesRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notes id not valid ! not found"));
		
		getnotes.setIsDeleted(false);
		getnotes.setDeletedOn(null);
		getnotes.setUpdatedOn(getnotes.getUpdatedOn());
		notesRespository.save(getnotes);
	}

	@Override
	public List<NotesDto> getUserRecycleBinNotes(Integer userid) {
	
		List<Notes> getnotes=notesRespository.findByCreatedByAndIsDeletedTrue(userid);
		
		List<NotesDto> notesdto=getnotes.stream().map(note-> mapper.map(note, NotesDto.class)).toList();
		
		return notesdto;
	}

	@Override
	public void hardDeleteNotesByid(Integer id) throws Exception {
		Notes getnotes=notesRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notes id not valid ! not found"));
		if(getnotes.getIsDeleted()) {
			notesRespository.delete(getnotes);
		}else {
			throw new Exception("You Cannot Delete Notes Directly");
		}
		
	}

	@Override
	public void userEmptyRecyclebin(Integer userid) {
		List<Notes> getnotes=notesRespository.findByCreatedByAndIsDeletedTrue(userid);
		if(!CollectionUtils.isEmpty(getnotes)) {
			notesRespository.deleteAll(getnotes);
		}
		
		
	}

	@Override
	public void favouriteNotes(Integer notesid) throws Exception {
	 int userid=1;
		Notes note=	notesRespository.findById(notesid).orElseThrow(()-> new ResourceNotFoundException("Notes id not valid ! not found"));
		FavouriteNote favouriteNote=FavouriteNote.builder()
				.userId(userid)
				.note(note)
				.build();
		favouriteNoteRepository.save(favouriteNote);
	 
	}

	@Override
	public void unFavouriteNotes(Integer Favournotesid) throws Exception {
		FavouriteNote favenote=	favouriteNoteRepository.findById(Favournotesid).orElseThrow(()-> new ResourceNotFoundException("Favourite Notes id not valid ! not found"));
		favouriteNoteRepository.delete(favenote);
		
	}

	@Override
	public List<FavouriteNotesDto> GetUserFavouriteNotes(Integer userId) {
		 int userid=1;
		 List<FavouriteNote> favouriteNotes= favouriteNoteRepository.findByUserId(userid);
		return favouriteNotes.stream().map(fn-> mapper.map(fn,FavouriteNotesDto.class)).toList();
	
	}

	@Override
	public boolean copyNotes(Integer notesId) throws Exception {
		
	Notes copynotes=notesRespository.findById(notesId).orElseThrow(()-> new ResourceNotFoundException("Notes id not valid !!not found"));
	
	Notes newnotes=Notes.builder()
			.title(copynotes.getTitle())
			.description(copynotes.getDescription())
			.category(copynotes.getCategory())
			.isDeleted(false)
			.deletedOn(null)
			.fileDetails(null)
			.build();
			Notes saveCopyNote=notesRespository.save(newnotes);
		if(!ObjectUtils.isEmpty(saveCopyNote)) {
			return true;
		}
		return false;
	}
	
	
	
}
