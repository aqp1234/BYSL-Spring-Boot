package com.kms.byslboot.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.byslboot.member.dto.LoginDTO;
import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.entity.Member;
import com.kms.byslboot.member.service.LoginService;
import com.kms.byslboot.member.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
	
	private static final String API_URL = "/api/member";
	
	@MockBean
	private MemberService memberService;
	
	@MockBean
	private LoginService loginService;
	
	@MockBean
	private PasswordEncoder passwordEncoder;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;
	
	private MemberDTO memberDTO;
	private Member member;
	
	@BeforeEach
	public void setup() { 
		this.mvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build(); 
		memberDTO = MemberDTO.builder()
				.email("test@naver.com")
				.password("password")
				.name("김민석")
				.phone("01011111111")
				.schoolName("동북고등학교")
				.locationName("서울특별시")
				.build();
		
		//member = memberDTO.toEntity(memberDTO, passwordEncoder);
		//member.setSchoolLocationCode("7010154", "B10");
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
		
		String content = objectMapper.writeValueAsString(memberDTO);
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
		
		String content = objectMapper.writeValueAsString(memberDTO);
		mvc.perform(post(API_URL)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	
	@Test
	@DisplayName("맴버 Valid 확인")
	@Disabled
	void checkMemberValid() throws Exception{
		
		String content = objectMapper.writeValueAsString(memberDTO);
		mvc.perform(post(API_URL)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
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
	//@Disabled
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
	
	@Test
	@DisplayName("로그인 테스트")
	@Disabled
	void login() throws Exception{
		LoginDTO login = new LoginDTO("test@naver.com", "password");
		
		String content = objectMapper.writeValueAsString(login);
		mvc.perform(post(API_URL + "/login")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("로그인 테스트(잘못된 비밀번호)")
	@Disabled
	void loginWrongPassword() throws Exception{
		LoginDTO login = new LoginDTO("test@naver.com", "password2");
		
		String content = objectMapper.writeValueAsString(login);
		mvc.perform(post(API_URL + "/login")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("로그인 테스트(없는 이메일)")
	@Disabled
	void loginWrongEmail() throws Exception{
		LoginDTO login = new LoginDTO("test123@naver.com", "password");
		
		String content = objectMapper.writeValueAsString(login);
		mvc.perform(post(API_URL + "/login")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
//	@Test
//	@Disabled
//	void test123() throws Exception{
//		List<MemberDTO> members = memberService.findAll();
//		System.out.println(members.size());
//		for(MemberDTO member : members) {
//			System.out.println(member.getId());
//		}
//	}
//	
//	@Test
//	@DisplayName("로그아웃 테스트")
//	@Disabled
//	void logout() throws Exception{
//		MemberDTO member = memberService.findMemberByEmail("test2@naver.com");
//		System.out.println(member);
//		loginService.login(member.getId());
//		mvc.perform(get(API_URL + "/logout"))
//			.andExpect(status().isOk());
//	}
}
