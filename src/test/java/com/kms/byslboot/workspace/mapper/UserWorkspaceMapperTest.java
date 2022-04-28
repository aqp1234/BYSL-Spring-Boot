package com.kms.byslboot.workspace.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.mock.web.MockHttpSession;

import com.kms.byslboot.workspace.dto.UserWorkspaceDTO;
import com.kms.byslboot.workspace.entity.UserWorkspace;
import com.kms.byslboot.workspace.exception.UserWorkspaceNotFoundException;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserWorkspaceMapperTest {
	
	@Autowired
	private UserWorkspaceMapper userWorkspaceMapper;
	
	private MockHttpSession session;
	private UserWorkspaceDTO userWorkspaceDTO;
	private UserWorkspace userWorkspace;
	
	@BeforeEach
	void setup() {
		userWorkspaceDTO = UserWorkspaceDTO.builder()
								.nick("test")
								.color("#000000")
								.build();
		session = new MockHttpSession();
		session.setAttribute(MEMBER_ID, 0);
		userWorkspace = userWorkspaceDTO.toEntity(userWorkspaceDTO, session, 1, 1);
	}
	
	@Test
	@DisplayName("유저워크스페이스 아이디 값으로 조회한다.")
	void findUserWorkspaceById() {
		UserWorkspace userWorkspaceEx = userWorkspaceMapper.findUserWorkspaceById(2).orElseThrow(UserWorkspaceNotFoundException::new);
		assertThat(userWorkspaceEx.getTeamId()).isEqualTo(49);
		assertThat(userWorkspaceEx.getWorkspaceId()).isEqualTo(3);
		assertThat(userWorkspaceEx.getUserId()).isEqualTo(11);
		assertThat(userWorkspaceEx.getId()).isEqualTo(2);
		assertThat(userWorkspaceEx.getColor()).isEqualTo("#ffa3a3");
	}
	
	@Test
	@DisplayName("유저워크스페이스 닉네임, 색상 변경 테스트")
	void updateUserWorkspace() {
		UserWorkspace userWorkspaceEx = userWorkspaceMapper.findUserWorkspaceById(2).orElseThrow(UserWorkspaceNotFoundException::new);
		UserWorkspace userWorkspaceUpdateBefore = UserWorkspace.builder()
												.id(userWorkspaceEx.getId())
												.nick("testupdate")
												.color("#123456")
												.build();
		userWorkspaceMapper.updateUserWorkspace(userWorkspaceUpdateBefore);
		UserWorkspace userWorkspaceUpdated = userWorkspaceMapper.findUserWorkspaceById(userWorkspaceUpdateBefore.getId()).orElseThrow(UserWorkspaceNotFoundException::new);
		assertThat(userWorkspaceUpdated.getNick()).isEqualTo(userWorkspaceUpdateBefore.getNick());
		assertThat(userWorkspaceUpdated.getColor()).isEqualTo(userWorkspaceUpdateBefore.getColor());
	}
	
	@Test
	@DisplayName("유저워크스페이스 삭제 테스트")
	void deleteUserWorkspace() {
		userWorkspaceMapper.deleteUserWorkspaceById(2);
		assertThat(userWorkspaceMapper.findUserWorkspaceByUserId(2)).isEmpty();
	}
	
	@Test
	@DisplayName("유저워크스페이스를 생성한다.")
	@Disabled("유저 워크스페이스만 생성 불가")
	void insertUserWorkspace() {
		userWorkspaceMapper.insertUserWorkspace(userWorkspace);
		UserWorkspace userWorkspaceEx = userWorkspaceMapper.findUserWorkspaceById(userWorkspace.getId()).orElseThrow(UserWorkspaceNotFoundException::new);

		assertThat(userWorkspaceEx.getTeamId()).isEqualTo(userWorkspace.getTeamId());
		assertThat(userWorkspaceEx.getWorkspaceId()).isEqualTo(userWorkspace.getWorkspaceId());
		assertThat(userWorkspaceEx.getUserId()).isEqualTo(userWorkspace.getUserId());
		assertThat(userWorkspaceEx.getId()).isEqualTo(userWorkspace.getId());
		assertThat(userWorkspaceEx.getColor()).isEqualTo(userWorkspace.getColor());
	}
}
