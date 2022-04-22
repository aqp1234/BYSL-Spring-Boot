package com.kms.byslboot.workspace.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.workspace.entity.Team;

@Mapper
public interface TeamMapper {
	int insertTeam(Team team);
	int insertAdminTeam(Team team);
	int insertGuestTeam(Team team);
	Optional<Team> findTeamById(int teamId);
	Optional<Team> findAdminTeamByWorkspaceId(int workspaceId);
	Optional<Team> findGuestTeamByWorkspaceId(int workspaceId);
	List<Team> findAllTeamByWorkspaceId(int workspaceId);
	void deleteTeamById(int teamId);
}
