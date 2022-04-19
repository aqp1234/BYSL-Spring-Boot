package com.kms.byslboot.workspace;

import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import com.kms.byslboot.workspace.dto.WorkspaceDTO;
import com.kms.byslboot.workspace.entity.Workspace;
import com.kms.byslboot.workspace.exception.WorkspaceNotFoundException;
import com.kms.byslboot.workspace.mapper.WorkspaceMapper;
import com.kms.byslboot.workspace.service.WorkspaceServiceImpl;

@ExtendWith(MockitoExtension.class)
public class WorkspaceServiceTest {
	
	private WorkspaceServiceImpl workspaceService;
	
	@Mock
	private WorkspaceMapper workspaceMapper;
	
	private MockHttpSession session;
	private WorkspaceDTO workspaceDTO;
	private Workspace workspace;
	
	@BeforeEach
	void setup() {
		session = new MockHttpSession();
		session.setAttribute(MEMBER_ID, 0);
		workspaceDTO = WorkspaceDTO.builder()
						.workspaceName("테스트")
						.build();
		workspace = Workspace.builder()
						.id(0)
						.workspaceName(workspaceDTO.getWorkspaceName())
						.ownerId(0)
						.createdAt(new Timestamp(System.currentTimeMillis()))
						.build();
		workspaceService = new WorkspaceServiceImpl(session, workspaceMapper);
	}
	
	@Test
	@DisplayName("워크스페이스 추가에 성공하면 생성된 워크스페이스의 아이디를 반환한다.")
	void insertWorkspace() {
		when(workspaceMapper.insertWorkspace(any(Workspace.class))).thenReturn(0);
		
		int workspaceId = workspaceService.insertWorkspace(workspaceDTO);
		assertThat(workspaceId).isEqualTo(0);
	}
	
	@Test
	@DisplayName("워크스페이스 아아디로 검색시 있는 아이디라면 정상적으로 조회하여 반환한다.")
	void findWorkspaceById() {
		when(workspaceMapper.findWorkspaceById(any(int.class))).thenReturn(Optional.of(workspace));
		
		Workspace findWorkspaceById = workspaceService.findWorkspaceById(0);
		assertThat(findWorkspaceById).isNotNull();
		assertThat(findWorkspaceById.getOwnerId()).isEqualTo(0);
		assertThat(findWorkspaceById.getWorkspaceName()).isEqualTo(workspace.getWorkspaceName());
	}
	
	@Test
	@DisplayName("워크스페이스 아이디로 검색시 없는 아이디라면 WorkspaceNotFoundException을 처리한다.")
	void findWorkspaceByIdFail() {
		when(workspaceMapper.findWorkspaceById(any(int.class))).thenReturn(Optional.empty());
		
		Assertions.assertThrows(WorkspaceNotFoundException.class, () -> {
			workspaceService.findWorkspaceById(0);
		});
	}
}
