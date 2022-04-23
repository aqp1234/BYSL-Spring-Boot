package com.kms.byslboot.workspace.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.kms.byslboot.workspace.dto.TeamPermissionRequest;
import com.kms.byslboot.workspace.dto.TeamPermissionResponse;

@MybatisTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TeamPermissionMapperTest {
	
	@Autowired
	private TeamPermissionMapper teamPermissionMapper;
	
	private List<Integer> allAvailableIds;
	private List<Integer> someAvailableIds;
	private List<Integer> someAvailableIds2;
	
	@BeforeEach
	void setup() {
		allAvailableIds = new ArrayList<>();
		for(int i = 1; i <= 25; i++) {
			allAvailableIds.add(i);
		}
		someAvailableIds = new ArrayList<>();
		for(int i = 1; i <= 10; i++) {
			someAvailableIds.add(i);
		}
		someAvailableIds2 = new ArrayList<>();
		for(int i = 1; i <= 5; i++) {
			someAvailableIds2.add(i);
		}
	}
	
	@Test
	@DisplayName("팀 생성 init으로 생성한다. 1~25 전부 False")
	void initTeamPermissionTest() {
		TeamPermissionRequest initTeamPermissionRequest = new TeamPermissionRequest(2, allAvailableIds);
		teamPermissionMapper.initTeamPermission(initTeamPermissionRequest);
		
		List<TeamPermissionResponse> teamPermissions = teamPermissionMapper.findTeamPermissionsByTeamId(2);
		assertThat(teamPermissions).allMatch((teamPermission) -> teamPermission.isAvailable() == false);
	}
	
	@Test
	@DisplayName("팀 생성 init 으로 생성 후 adminTeam 으로 업데이트 한다. 이 경우 1~25 전부 True")
	void updateAdminTeamPermission() {
		TeamPermissionRequest initTeamPermissionRequest = new TeamPermissionRequest(2, allAvailableIds);
		teamPermissionMapper.initTeamPermission(initTeamPermissionRequest);
		
		teamPermissionMapper.updateAdminTeamPermission(2);

		List<TeamPermissionResponse> teamPermissions = teamPermissionMapper.findTeamPermissionsByTeamId(2);
		assertThat(teamPermissions).allMatch((teamPermission) -> teamPermission.isAvailable() == true);
	}
	
	@Test
	@DisplayName("팀 생성 init으로 생성 후 1~10까지 권한을 부여한다.")
	void updateAvailableToTrue() {
		TeamPermissionRequest initTeamPermissionRequest = new TeamPermissionRequest(2, allAvailableIds);
		teamPermissionMapper.initTeamPermission(initTeamPermissionRequest);

		TeamPermissionRequest someTeamPermissionRequest = new TeamPermissionRequest(2, someAvailableIds);
		
		teamPermissionMapper.updateAvailableToTrue(someTeamPermissionRequest);

		List<TeamPermissionResponse> teamPermissions = teamPermissionMapper.findTeamPermissionsByTeamId(2);
		for(int i = 0; i <= 9; i++) {
			assertThat(teamPermissions.get(i).isAvailable()).isEqualTo(true);
		}
		for(int i = 10; i <= 24; i++) {
			assertThat(teamPermissions.get(i).isAvailable()).isEqualTo(false);
		}
	}
	
	@Test
	@DisplayName("팀 생성 init으로 생성 후 1~10까지 권한을 부여후 1~5 권한은 취소한다.")
	void updateAvailableToFalse() {
		TeamPermissionRequest initTeamPermissionRequest = new TeamPermissionRequest(2, allAvailableIds);
		teamPermissionMapper.initTeamPermission(initTeamPermissionRequest);

		TeamPermissionRequest someTeamPermissionRequest = new TeamPermissionRequest(2, someAvailableIds);
		TeamPermissionRequest someTeamPermissionRequest2 = new TeamPermissionRequest(2, someAvailableIds2);
		
		teamPermissionMapper.updateAvailableToTrue(someTeamPermissionRequest);
		teamPermissionMapper.updateAvailableToFalse(someTeamPermissionRequest2);

		List<TeamPermissionResponse> teamPermissions = teamPermissionMapper.findTeamPermissionsByTeamId(2);
		for(int i = 0; i <= 4; i++) {
			assertThat(teamPermissions.get(i).isAvailable()).isEqualTo(false);
		}
		for(int i = 5; i <= 9; i++) {
			assertThat(teamPermissions.get(i).isAvailable()).isEqualTo(true);
		}
		for(int i = 10; i <= 24; i++) {
			assertThat(teamPermissions.get(i).isAvailable()).isEqualTo(false);
		}
	}
}
