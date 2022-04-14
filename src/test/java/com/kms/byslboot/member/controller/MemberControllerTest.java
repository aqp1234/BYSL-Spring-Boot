package com.kms.byslboot.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.byslboot.member.dto.MemberDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
	
	private static final String API_URL = "/api/member";

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mvc;
	
	@BeforeEach()
	public void setup() { 
		this.mvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build(); 
	}
	
	@Test
	@DisplayName("get All Members")
	@Disabled
	void getAllTest() throws Exception {
		mvc.perform(get(API_URL))
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("회원 가입 테스트")
	@Disabled
	void insertMemberTest() throws Exception {
		MemberDTO member = new MemberDTO();
		member.setEmail("test9@naver.com");
		member.setPassword("password");
		member.setName("김민석");
		member.setPhone("01011111119");
		member.setSchoolName("동북고등학교");
		member.setLocationName("서울특별시");
		
		String content = objectMapper.writeValueAsString(member);
		mvc.perform(post(API_URL)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("회원 가입 테스트(중복 이메일 가입 시도)")
	@Disabled
	void insertMemberTest2() throws Exception {
		MemberDTO member = new MemberDTO();
		member.setEmail("test9@naver.com");
		member.setPassword("password");
		member.setName("김민석");
		member.setPhone("01011111119");
		member.setSchoolName("동북고등학교");
		member.setLocationName("서울특별시");
		
		String content = objectMapper.writeValueAsString(member);
		mvc.perform(post(API_URL)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	
	@Test
	@DisplayName("이메일 중복 테스트")
	@Disabled
	void checkDuplicatedByEmail() throws Exception{
		String email = "test999@naver.com";
		mvc.perform(post(API_URL + "/checkEmail")
				.content(email)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
				
	}

	@Test
	@DisplayName("핸드폰 번호 중복 테스트")
	@Disabled
	void checkDuplicatedByPhone() throws Exception{
		String phone = "01011111120";
		mvc.perform(post(API_URL + "/checkPhone")
				.content(phone)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
				
	}
	
	@Test
	@DisplayName("맴버 ID 를 통해 멤버 검색")
	@Disabled
	void findMemberByIdTest() throws Exception {
		mvc.perform(get(API_URL + "/5"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("맴버 ID 를 통해 멤버 검색(없는 ID)")
	@Disabled
	void findMemberByIdTest2() throws Exception {
		mvc.perform(get(API_URL + "/1"))
			.andExpect(status().isNotFound());
	}
}
