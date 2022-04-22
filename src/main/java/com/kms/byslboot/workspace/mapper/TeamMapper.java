package com.kms.byslboot.workspace.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.workspace.entity.Team;

@Mapper
public interface TeamMapper {
	public void insertTeam(Team team);
	public void insertAdminTeam(Team team);
	public void insertGuestTeam(Team team);
	public Optional<Team> findTeamById(int teamId);
	public Optional<Team> findAdminTeamByWorkspaceId(int workspaceId);
	public Optional<Team> findGuestTeamByWorkspaceId(int workspaceId);
	public List<Team> findAllTeamByWorkspaceId(int workspaceId);
	public void deleteTeamById(int teamId);
}
