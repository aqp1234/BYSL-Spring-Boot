package com.kms.byslboot.workspace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kms.byslboot.workspace.dto.TeamDTO;
import com.kms.byslboot.workspace.entity.Team;
import com.kms.byslboot.workspace.exception.TeamNotFoundException;
import com.kms.byslboot.workspace.mapper.TeamMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{
	
	private TeamMapper teamMapper;

	@Override
	public int teamInsert(TeamDTO teamDTO, int workspaceId) {
		Team team = teamDTO.toEntity(teamDTO, workspaceId);
		int teamId = teamMapper.insertTeam(team);
		return teamId;
	}

	@Override
	public int adminTeamInsert(TeamDTO teamDTO, int workspaceId) {
		Team team = teamDTO.toAdminEntity(teamDTO, workspaceId);
		int teamId = teamMapper.insertAdminTeam(team);
		return teamId;
	}

	@Override
	public int guestTeamInsert(TeamDTO teamDTO, int workspaceId) {
		Team team = teamDTO.toGuestEntity(teamDTO, workspaceId);
		int teamId = teamMapper.insertGuestTeam(team);
		return teamId;
	}

	@Override
	public Team teamSelectById(int teamId) {
		return teamMapper.findTeamById(teamId).orElseThrow(TeamNotFoundException::new);
	}

	@Override
	public Team adminTeamSelectByWorkspaceId(int workspaceId) {
		return teamMapper.findAdminTeamByWorkspaceId(workspaceId).orElseThrow(TeamNotFoundException::new);
	}

	@Override
	public Team guestTeamSelectByWorkspaceId(int workspaceId) {
		return teamMapper.findGuestTeamByWorkspaceId(workspaceId).orElseThrow(TeamNotFoundException::new);
	}

	@Override
	public List<Team> allTeamSelectByWorkspaceId(int workspaceId) {
		return teamMapper.findAllTeamByWorkspaceId(workspaceId);
	}

	@Override
	public void teamDeleteById(int teamId) {
		teamMapper.deleteTeamById(teamId);
	}
	
	
}
