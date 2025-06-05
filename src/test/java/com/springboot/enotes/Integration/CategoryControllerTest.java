package com.springboot.enotes.Integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.enotes.Dto.CategoryDto;
import com.springboot.enotes.Dto.LoginRequest;
import com.springboot.enotes.Entity.Category;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	

	CategoryDto categoryDto=null;
	Category category=null;
	
	@BeforeEach
	public void intialize() {
		
		categoryDto=CategoryDto.builder()
				.id(null)
				.name("Phython Notes")
				.description("Phython Notes")
				.isActive(true).build();
		

		category=Category.builder()
				.id(null)
				.name("Phython Notes")
				.description("Phython Notes")
				.isActive(true)
				.isDeleted(false)
				.build();
		
	}

@Test
	public void testSaveCategory() throws JsonProcessingException, Exception {
		
	String token=generateToken("gouravvohra3@gmail.com","1234");
	
		mockMvc.perform(post("/api/vi/category/save-category")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryDto))
				.header("Authorization", token)
				).andExpect(status().isCreated())
		.andExpect(jsonPath("$.message").value("SuccessFully Saved"))
		.andExpect(jsonPath("$.status").value("Success"));
		
		
	}
	
	public String generateToken(String email,String password) throws JsonProcessingException, UnsupportedEncodingException, Exception {
		
		LoginRequest loginreq=new LoginRequest();
		loginreq.setEmail(email);
		loginreq.setPassword(password);
		
	String response=mockMvc.perform(post("/api/vi/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginreq))
				).andExpect(status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString();
JsonNode root = objectMapper.readTree(response);
		String token = root.path("data").path("token").asText();
		return "Bearer "+token;
	}
	
	
}
