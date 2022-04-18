package com.kms.byslboot.member;

import static com.kms.byslboot.fixture.MemberFixture.MEMBER1;
import static com.kms.byslboot.member.service.LoginServiceImpl.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import com.kms.byslboot.member.dto.LoginDTO;
import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.exception.MemberNotFoundException;
import com.kms.byslboot.member.service.LoginServiceImpl;
import com.kms.byslboot.member.service.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
	
	private LoginServiceImpl loginService;

	@Mock
	private MemberServiceImpl memberService;
	
	private MockHttpSession mockHttpSession;
	
	private MemberDTO memberDTO;
	
	private LoginDTO loginDTO;
	
	@BeforeEach
	void setup() {
		memberDTO = MemberDTO.builder()
						.email("test@naver.com")
						.password("password")
						.name("김민석")
						.phone("01011111111")
						.schoolName("동북고등학교")
						.locationName("서울특별시")
						.build();
		
		loginDTO = LoginDTO.builder()
					.email("test@naver.com")
					.password("password")
					.build();
		
		mockHttpSession = new MockHttpSession();
		loginService = new LoginServiceImpl(mockHttpSession, memberService);
	}
	
	@Test
	@DisplayName("로그인 성공시 세션 값 확인")
	void loginTest() {
		loginService.login(0);
		
		assertThat(mockHttpSession.getAttribute(MEMBER_ID)).isNotNull();
		assertThat(mockHttpSession.getAttribute(MEMBER_ID)).isEqualTo(0);
	}
	
	@Test
	@DisplayName("로그아웃 성공시 세션 값 확인")
	void logoutTest() {
		mockHttpSession.setAttribute(MEMBER_ID, 0);
		
		loginService.logout();
		
		assertThat(mockHttpSession.getAttribute(MEMBER_ID)).isNull();
	}
	
	@Test
	@DisplayName("로그인 된 사용자 정보 가져오기")
	void getLoginMember() {
		mockHttpSession.setAttribute(MEMBER_ID, 0);
		
		when(memberService.findMemberById(any(int.class))).thenReturn(MEMBER1);
		
		assertThat(loginService.getLoginMember()).isEqualTo(MEMBER1);
	}
	
	@Test
	@DisplayName("로그인 된 사용자 정보 가져오기(사용자 검색 실패 시)")
	void getLoginMemberFail() {
		mockHttpSession.setAttribute(MEMBER_ID, 0);
		
		when(memberService.findMemberById(any(int.class))).thenThrow(MemberNotFoundException.class);
		
		Assertions.assertThrows(MemberNotFoundException.class, () -> {
			loginService.getLoginMember();
		});
	}
}
