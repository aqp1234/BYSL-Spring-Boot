package com.kms.byslboot.workspace.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.kms.byslboot.workspace.dto.TeamDTO;
import com.kms.byslboot.workspace.entity.Team;
import com.kms.byslboot.workspace.exception.TeamNotFoundException;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TeamMapperTest {
	
	@Autowired
	private TeamMapper teamMapper;
	
	@Test
	@DisplayName("데이터베이스에 있는 아이디가 2번인 팀 정보를 검색한다.")
	void findTeamById() {
		Team team = teamMapper.findTeamById(2).orElseThrow(TeamNotFoundException::new);
		assertThat(team.getWorkspaceId()).isEqualTo(1);
		assertThat(team.getName()).isEqualTo("testGeneralTeam");
		assertThat(team.isAdmin()).isEqualTo(false);
		assertThat(team.isGuest()).isEqualTo(false);
	}
	
	@Test
	@DisplayName("데이터베이스에 없는 아이디를 검색시 TeamNotFoundException 에러를 처리한다.")
	void findTeamByIdFail() {
		Assertions.assertThrows(TeamNotFoundException.class, () -> {
			teamMapper.findTeamById(1).orElseThrow(TeamNotFoundException::new);
		});
	}
	
	@Test
	@DisplayName("데이터베이스에 일반 팀 정보를 추가한다.")
	void insertTeam() {
		TeamDTO teamDTO = TeamDTO.builder()
							.name("insertTestTeam")
							.build();
		Team teamex = teamDTO.toEntity(teamDTO, 1);
		teamMapper.insertTeam(teamex);
		Team team = teamMapper.findTeamById(teamex.getId()).orElseThrow(TeamNotFoundException::new);
		assertThat(team.getWorkspaceId()).isEqualTo(1);
		assertThat(team.getName()).isEqualTo(teamDTO.getName());
		assertThat(team.isAdmin()).isEqualTo(false);
		assertThat(team.isGuest()).isEqualTo(false);
	}
	
	@Test
	@DisplayName("데이터베이스에 어드민 팀 정보를 추가한다.")
	void insertAdminTeam() {
		TeamDTO teamDTO = TeamDTO.builder()
							.name("insertAdminTestTeam")
							.build();
		Team teamex = teamDTO.toEntity(teamDTO, 1);
		teamMapper.insertAdminTeam(teamex);
		Team team = teamMapper.findTeamById(teamex.getId()).orElseThrow(TeamNotFoundException::new);
		assertThat(team.getWorkspaceId()).isEqualTo(1);
		assertThat(team.getName()).isEqualTo(teamDTO.getName());
		assertThat(team.isAdmin()).isEqualTo(true);
		assertThat(team.isGuest()).isEqualTo(false);
	}
	
	@Test
	@DisplayName("데이터베이스에 게스트 팀 정보를 추가한다.")
	void insertGuestTeam() {
		TeamDTO teamDTO = TeamDTO.builder()
							.name("insertGuestTestTeam")
							.build();
		Team teamEx = teamDTO.toEntity(teamDTO, 1);
		teamMapper.insertGuestTeam(teamEx);
		Team team = teamMapper.findTeamById(teamEx.getId()).orElseThrow(TeamNotFoundException::new);
		assertThat(team.getWorkspaceId()).isEqualTo(1);
		assertThat(team.getName()).isEqualTo(teamDTO.getName());
		assertThat(team.isAdmin()).isEqualTo(false);
		assertThat(team.isGuest()).isEqualTo(true);
	}
	
	@Test
	@DisplayName("워크스페이스의 어드민 팀 정보를 가져온다.")
	void findAdminTeamByWorkspaceId() {
		Team adminTeam = teamMapper.findAdminTeamByWorkspaceId(1).orElseThrow(TeamNotFoundException::new);
		assertThat(adminTeam.getWorkspaceId()).isEqualTo(1);
		assertThat(adminTeam.getName()).isEqualTo("testAdminTeam");
	}
	
	@Test
	@DisplayName("워크스페이스의 게스트 팀 정보를 가져온다.")
	void findGuestTeamByWorkspaceId() {
		Team adminTeam = teamMapper.findGuestTeamByWorkspaceId(1).orElseThrow(TeamNotFoundException::new);
		assertThat(adminTeam.getWorkspaceId()).isEqualTo(1);
		assertThat(adminTeam.getName()).isEqualTo("testGuestTeam");
	}
	
	@Test
	@DisplayName("워크스페이스의 모든 팀 정보를 가져온다.")
	void findAllTeamByWorkspaceId() {
		Team adminTeam = teamMapper.findAdminTeamByWorkspaceId(1).orElseThrow(TeamNotFoundException::new);
		Team guestTeam = teamMapper.findGuestTeamByWorkspaceId(1).orElseThrow(TeamNotFoundException::new);
		List<Team> teams = teamMapper.findAllTeamByWorkspaceId(1);
		assertThat(teams.size()).isEqualTo(3);
		assertThat(teams).anyMatch((team) -> team.getId() == adminTeam.getId());
		assertThat(teams).anyMatch((team) -> team.getId() == guestTeam.getId());
	}
}
