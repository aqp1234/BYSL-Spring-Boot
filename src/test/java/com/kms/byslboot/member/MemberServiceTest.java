package com.kms.byslboot.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.kms.byslboot.member.dto.LoginDTO;
import com.kms.byslboot.member.dto.MemberDTO;
import com.kms.byslboot.member.entity.Member;
import com.kms.byslboot.member.exception.DuplicatedKeyException;
import com.kms.byslboot.member.exception.MemberNotFoundException;
import com.kms.byslboot.member.mapper.MemberMapper;
import com.kms.byslboot.member.service.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
	
	@InjectMocks
	private MemberServiceImpl memberService;
	
	@Mock
	private MemberMapper memberMapper;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	private MemberDTO memberDTO;
	
	private Member member;
	
	private LoginDTO loginDTO;
	
	@BeforeEach
	void setup() {
		ReflectionTestUtils.setField(memberService, "NICE_KEY", "77e0f523027c453ab55f63fd974b349b");
		when(passwordEncoder.encode(any())).thenReturn("password");
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
	}
	
	@Test
	@DisplayName("이메일로 회원 검색 테스트")
	void findMemberByEmailTest(){
		when(memberMapper.findMemberByEmail(any(String.class))).thenReturn(Optional.of(member));
		
		Member findMemberByEmailMember = memberService.findMemberByEmail(member.getEmail());

		assertThat(findMemberByEmailMember).isNotNull();
		assertThat(findMemberByEmailMember.getId()).isEqualTo(member.getId());
		assertThat(findMemberByEmailMember.getEmail()).isEqualTo(member.getEmail());
	}
	
	@Test
	@DisplayName("맴버 추가 테스트")
	@Disabled
	void insertMemberTest() {
		when(memberMapper.insertMember(any(Member.class))).thenReturn(1);
		
		assertThat(memberService.insertMember(memberDTO)).isEqualTo(1);
	}
	
	@Test
	@DisplayName("맴버 아이디를 통한 검색(성공)")
	void findMemberByIdTest() {
		when(memberMapper.findMemberById(any(int.class))).thenReturn(Optional.of(member));
		
		Member findMemberById = memberService.findMemberById(member.getId());
		
		assertThat(findMemberById).isNotNull();
		assertThat(findMemberById.getEmail()).isEqualTo(member.getEmail());
	}
	
	@Test
	@DisplayName("맴버 아이디를 통한 검색(없는 아이디)")
	void findMemberByIdTestFail() {
		when(memberMapper.findMemberById(any(int.class))).thenReturn(Optional.empty());
		
		Assertions.assertThrows(MemberNotFoundException.class, () -> {
			memberService.findMemberById(member.getId());
		});
	}
	
	@Test
	@DisplayName("이메일을 통해 가입여부 확인(첫 가입인 경우)")
	void existsByEmailTest() {
		when(memberMapper.existsByEmail(any(String.class))).thenReturn(false);

		memberService.existsByEmail(member.getEmail());
		
		verify(memberMapper, times(1)).existsByEmail(member.getEmail());
	}
	
	@Test
	@DisplayName("이메일을 통해 가입여부 확인(이미 있는 경우)")
	void existsByEmailTest2() {
		when(memberMapper.existsByEmail(any(String.class))).thenThrow(DuplicatedKeyException.class);
		
		Assertions.assertThrows(DuplicatedKeyException.class, () -> {
			memberService.existsByEmail(member.getEmail());
		});
	}
	
	@Test
	@DisplayName("패스워드 동일한지 체크(동일 시)")
	void checkPasswordTest() {
		when(memberMapper.findMemberByEmail(any())).thenReturn(Optional.of(member));
		when(passwordEncoder.matches(any(), any())).thenReturn(true);
		
		assertThat(memberService.checkPassword(loginDTO)).isEqualTo(true);
	}
	
	@Test
	@DisplayName("패스워드 동일한지 체크(틀릴 시)")
	void checkPasswordTestFail() {
		when(memberMapper.findMemberByEmail(any())).thenReturn(Optional.of(member));
		when(passwordEncoder.matches(any(), any())).thenReturn(false);
		
		assertThat(memberService.checkPassword(loginDTO)).isEqualTo(false);
	}
	
	@Test
	@DisplayName("패스워드 동일한지 체크(없는 이메일)")
	void checkPasswordTestNoEmail() {
		when(memberMapper.findMemberByEmail(any())).thenReturn(Optional.empty());
		
		Assertions.assertThrows(MemberNotFoundException.class, () -> {
			memberService.checkPassword(loginDTO);
		});
	}
}
