package com.kms.byslboot.dashboard.mapper;

import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.mock.web.MockHttpSession;

import com.kms.byslboot.dashboard.dto.DashboardRequest;
import com.kms.byslboot.dashboard.dto.DashboardResponse;
import com.kms.byslboot.dashboard.entity.Dashboard;
import com.kms.byslboot.dashboard.exception.DashboardNotFoundException;
import com.kms.byslboot.workspace.entity.UserWorkspace;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DashboardMapperTest {
	
	@Autowired
	private DashboardMapper dashboardMapper;
	
	@Mock
	private UserWorkspaceService userWorkspaceService;
	
	private MockHttpSession session;
	private DashboardRequest dashboardRequest;
	private DashboardRequest dashboardRequestUpdate;
	private Dashboard dashboard;
	private Dashboard dashboardUpdate;
	private UserWorkspace userWorkspace;

	@BeforeEach
	void setUp() throws Exception {
		session = new MockHttpSession();
		session.setAttribute(MEMBER_ID, 11);
		
		userWorkspace = UserWorkspace.builder()
							.id(2)
							.userId(11)
							.workspaceId(3)
							.teamId(49)
							.nick("김민석")
							.color("#ffa3a3")
							.build();
		
		dashboardRequest = DashboardRequest.builder()
								.managerUserWorkspaceId(2)
								.subject("subjectTest")
								.content("contentTest")
								.startDate("2022-05-02")
								.endDate("2022-05-03")
								.flag(1)
								.build();
		
		dashboardRequestUpdate = DashboardRequest.builder()
									.managerUserWorkspaceId(2)
									.subject("subjectTest")
									.content("contentTest")
									.startDate("2022-05-02")
									.endDate("2022-05-03")
									.flag(1)
									.build();
		
		when(userWorkspaceService.findConnectedUserWorkspace(any(int.class))).thenReturn(userWorkspace);
		
		dashboard = dashboardRequest.toEntity(dashboardRequest, 3, userWorkspaceService);
	}

	@Test
	@DisplayName("대시보드 추가 테스트")
	void insertDashboard() {
		dashboardMapper.insertDashboard(dashboard);
		assertThat(dashboardMapper.findDashboardById(dashboard.getId())).isNotEmpty();
	}
	
	@Test
	@DisplayName("대시보드 조회 테스트")
	void findDashboardById() {
		dashboardMapper.insertDashboard(dashboard);
		DashboardResponse dashboardResponse = dashboardMapper.findDashboardById(dashboard.getId()).orElseThrow(DashboardNotFoundException::new);
		assertThat(dashboardResponse.getManagerUserWorkspaceId()).isEqualTo(dashboardRequest.getManagerUserWorkspaceId());
		assertThat(dashboardResponse.getManagerNick()).isEqualTo(userWorkspace.getNick());
		assertThat(dashboardResponse.getManagerColor()).isEqualTo(userWorkspace.getColor());
		assertThat(dashboardResponse.getSubject()).isEqualTo(dashboardRequest.getSubject());
		assertThat(dashboardResponse.getContent()).isEqualTo(dashboardRequest.getContent());
		assertThat(dashboardResponse.getStartDate()).isEqualTo(Date.valueOf(dashboardRequest.getStartDate()));
		assertThat(dashboardResponse.getEndDate()).isEqualTo(Date.valueOf(dashboardRequest.getEndDate()));
		assertThat(dashboardResponse.getFlag()).isEqualTo(dashboardRequest.getFlag());
	}
	
	@Test
	@DisplayName("대시보드 리스트 조회 테스트")
	void findDashboardByWorkspaceId() {
		dashboardMapper.insertDashboard(dashboard);
		dashboardMapper.insertDashboard(dashboard);
		assertThat(dashboardMapper.findDashboardByWorkspaceId(dashboard.getWorkspaceId()).size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("대시보드 업데이트 테스트")
	void updateDashboard() {
		dashboardMapper.insertDashboard(dashboard);
		dashboardUpdate = dashboardRequestUpdate.toEntity(dashboardRequest, dashboard.getId(), userWorkspace.getWorkspaceId(), userWorkspaceService);
		dashboardMapper.updateDashboard(dashboardUpdate);
		DashboardResponse dashboardResponse = dashboardMapper.findDashboardById(dashboardUpdate.getId()).orElseThrow(DashboardNotFoundException::new);

		assertThat(dashboardResponse.getManagerUserWorkspaceId()).isEqualTo(dashboardRequestUpdate.getManagerUserWorkspaceId());
		assertThat(dashboardResponse.getSubject()).isEqualTo(dashboardRequestUpdate.getSubject());
		assertThat(dashboardResponse.getContent()).isEqualTo(dashboardRequestUpdate.getContent());
		assertThat(dashboardResponse.getStartDate()).isEqualTo(Date.valueOf(dashboardRequestUpdate.getStartDate()));
		assertThat(dashboardResponse.getEndDate()).isEqualTo(Date.valueOf(dashboardRequestUpdate.getEndDate()));
		assertThat(dashboardResponse.getFlag()).isEqualTo(dashboardRequestUpdate.getFlag());
	}
	
	@Test
	@DisplayName("대시보드 삭제 테스트")
	void deleteDashboard() {
		dashboardMapper.insertDashboard(dashboard);
		dashboardMapper.deleteDashboardById(dashboard.getId());
		assertThat(dashboardMapper.findDashboardById(dashboard.getId())).isEmpty();
	}
}
