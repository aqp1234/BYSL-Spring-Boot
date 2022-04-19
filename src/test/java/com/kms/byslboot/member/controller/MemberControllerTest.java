package com.kms.byslboot.member.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static com.kms.byslboot.fixture.MemberFixture.MEMBER1;
import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.byslboot.member.dto.LoginDTO;
import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.entity.Member;
import com.kms.byslboot.member.exception.DuplicatedKeyException;
import com.kms.byslboot.member.exception.MemberNotFoundException;
import com.kms.byslboot.member.exception.UnAuthenticatedException;
import com.kms.byslboot.member.service.LoginService;
import com.kms.byslboot.member.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
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
	
	private MockHttpSession mockHttpSession;
	private MemberDTO memberDTO;
	private LoginDTO loginDTO;
	private Member member;
	
	@BeforeEach
	public void setup() { 
		this.mvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				//.alwaysDo(print())
				.build(); 
		memberDTO = MemberDTO.builder()
				.email("test@naver.com")
				.password("password")
				.name("김민석")
				.phone("01011111111")
				.schoolName("동북고등학교")
				.locationName("서울특별시")
				.build();
		
		member = memberDTO.toEntity(memberDTO, passwordEncoder);
		member.setSchoolLocationCode("7010154", "B10");
		
		loginDTO = LoginDTO.builder()
					.email("test@naver.com")
					.password("password")
					.build();
		
		mockHttpSession = new MockHttpSession();
	}
	
	@Test
	@DisplayName("get All Members (테스트)")
	void getAllTest() throws Exception {
		List<Member> exmembers = new ArrayList<Member>();
		exmembers.add(MEMBER1);
		
		when(memberService.findAll()).thenReturn(exmembers);
		
		mvc.perform(get(API_URL))
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("회원 가입 성공시 201 상태코드를 반환하고, 헤더에 Location 값으로 리다이렉션할 값, Content-Location 값으로 추가한 멤버의 정보 위치를 알려준다")
	void insertMemberTest() throws Exception {
		String content = objectMapper.writeValueAsString(memberDTO);
		
		doNothing().when(memberService).existsByEmail(any(String.class));
		doNothing().when(memberService).existsByPhone(any(String.class));
		when(memberService.insertMember(any(MemberDTO.class))).thenReturn(MEMBER1.getId());
		when(memberService.findMemberById(MEMBER1.getId())).thenReturn(MEMBER1);
		
		mvc.perform(post(API_URL)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "/"))
				.andExpect(header().string("Content-Location", "/api/member/" + MEMBER1.getId()));
	}
	
	@Test
	@DisplayName("중복 이메일로 가입시 DuplicatedKeyException 에러를 처리하고 409 상태코드를 반환한다")
	void insertMemberTest2() throws Exception {
		String content = objectMapper.writeValueAsString(memberDTO);
		
		doThrow(DuplicatedKeyException.class).when(memberService).existsByEmail(MEMBER1.getEmail());
		
		mvc.perform(post(API_URL)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	
	@Test
	@DisplayName("맴버 Valid 확인")
	@Disabled // Validation 테스트로 변경해야됨
	void checkMemberValid() throws Exception{
		String content = objectMapper.writeValueAsString(memberDTO);
		
		mvc.perform(post(API_URL)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("동일한 이메일이 없는 경우 200 상태코드를 반환한다.")
	void checkDuplicatedByEmail() throws Exception{
		String email = "test999@naver.com";
		
		doNothing().when(memberService).existsByEmail(any(String.class));
		
		mvc.perform(post(API_URL + "/checkEmail")
				.content(email)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("동일한 이메일이 있는 경우 DuplicatedKeyException 에러를 처리하고 409 상태코드를 반환한다.")
	void checkDuplicatedByEmailFail() throws Exception{
		String email = "test999@naver.com";

		doThrow(DuplicatedKeyException.class).when(memberService).existsByEmail(any(String.class));
		
		mvc.perform(post(API_URL + "/checkEmail")
				.content(email)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	@Test
	@DisplayName("동일한 핸드폰이 없는 경우 200 상태코드를 반환한다.")
	void checkDuplicatedByPhone() throws Exception{
		String phone = "01011111120";
		
		doNothing().when(memberService).existsByPhone(any(String.class));
		
		mvc.perform(post(API_URL + "/checkPhone")
				.content(phone)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("동일한 핸드폰이 있는 경우 DuplicatedKeyException 에러를 처리하고 409 상태코드를 반환한다.")
	void checkDuplicatedByPhoneFail() throws Exception{
		String phone = "01011111120";

		doThrow(DuplicatedKeyException.class).when(memberService).existsByPhone(any(String.class));
		
		mvc.perform(post(API_URL + "/checkPhone")
				.content(phone)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	
	@Test
	@DisplayName("맴버 아이디로 검색이 성공하면 맴버객체와 200 상태코드를 반환한다.")
	void findMemberByIdTest() throws Exception {
		when(memberService.findMemberById(any(int.class))).thenReturn(MEMBER1);
		
		mvc.perform(get(API_URL + "/5"))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(MEMBER1)));
	}

	@Test
	@DisplayName("없는 맴버 아이디로 검색을 요청하면 MemberNotFoundException 에러를 처리하고 404 상태코드를 반환한다.")
	void findMemberByIdTestFail() throws Exception {
		when(memberService.findMemberById(any(int.class))).thenThrow(MemberNotFoundException.class);
		
		mvc.perform(get(API_URL + "/1"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("로그인에 성공하면 200 상태코드를 반환한다.")
	void login() throws Exception{
		String content = objectMapper.writeValueAsString(loginDTO);
		
		when(memberService.checkPassword(any(LoginDTO.class))).thenReturn(true);
		when(memberService.findMemberByEmail(any(String.class))).thenReturn(MEMBER1);
		doNothing().when(loginService).login(any(int.class));
		
		mvc.perform(post(API_URL + "/login")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("로그인 시 잘못된 비밀번호를 입력시 400 상태코드를 반환한다.")
	void loginWrongPassword() throws Exception{
		String content = objectMapper.writeValueAsString(loginDTO);
		
		when(memberService.checkPassword(any(LoginDTO.class))).thenReturn(false);
		
		mvc.perform(post(API_URL + "/login")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("로그인 시 없는 이메일로 로그인을 시도시 MemberNotFoundException 에러를 처리하고 404 상태코드를 반환한다.")
	void loginWrongEmail() throws Exception{		
		String content = objectMapper.writeValueAsString(loginDTO);
		
		when(memberService.checkPassword(any(LoginDTO.class))).thenThrow(MemberNotFoundException.class);
		
		mvc.perform(post(API_URL + "/login")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("로그아웃 요청시 session에 있는 MEMBER_ID 값을 삭제하고 200 상태코드를 반환한다.")
	void logoutTest() throws Exception{
		mockHttpSession.setAttribute(MEMBER_ID, 0);
		
		when(loginService.getLoginMemberID()).thenReturn(0);
		
		mvc.perform(get(API_URL + "/logout")
				.session(mockHttpSession))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("LoginRequired 어노테이션이 있을 때 session에 값이 없으면 UnAuthenticatedException 에러를 처리하고 401 상태코드를 반환한다.")
	void logoutTestNotLogined() throws Exception{
		when(loginService.getLoginMemberID()).thenReturn(null);
		
		mvc.perform(get(API_URL + "/logout"))
				.andExpect(status().isUnauthorized());
	}
	
}
