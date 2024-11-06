package com.springboot.enotes.ServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.print.DocFlavor.INPUT_STREAM;

import org.apache.catalina.mapper.Mapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.enotes.Dto.NotesDto;
import com.springboot.enotes.Dto.NotesDto.CategoryDto;
import com.springboot.enotes.Entity.FileDetails;
import com.springboot.enotes.Entity.Notes;
import com.springboot.enotes.Exception.ResourceNotFoundException;
import com.springboot.enotes.Repository.CategoryRepository;
import com.springboot.enotes.Repository.FileRepository;
import com.springboot.enotes.Repository.NotesRespository;
import com.springboot.enotes.Service.NotesSave;

@Service
public class NotesSaveImpl implements NotesSave {

	@Autowired
	private NotesRespository notesRespository;

	@Autowired
	private CategoryRepository categoryRespository;

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
	
		
		checkcategoryExist(notesdto.getCategory());

		Notes savenotes=mapper.map(notesdto, Notes.class);

		FileDetails filedlts=saveFileDetails(file);
		if(!ObjectUtils.isEmpty(filedlts)) {
			savenotes.setFileDetails(filedlts);
		}else {
			savenotes.setFileDetails(null);
		}
		
	Notes n=notesRespository.save(savenotes);
if(ObjectUtils.isEmpty(n)) {
	return false;
}
		return true;
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
	
	
	
}
