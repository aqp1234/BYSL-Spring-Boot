package com.kms.byslboot.workspace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kms.byslboot.workspace.dto.TeamPermissionRequest;
import com.kms.byslboot.workspace.dto.TeamPermissionResponse;
import com.kms.byslboot.workspace.mapper.TeamPermissionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamPermissionServiceImpl implements TeamPermissionService{
	
	private final TeamPermissionMapper teamPermissionMapper;

	@Override
	public void initTeamPermission(TeamPermissionRequest initTeamPermissionRequest) {
		teamPermissionMapper.initTeamPermission(initTeamPermissionRequest);
	}

	@Override
	public void updateAdminTeamPermission(int teamId) {
		teamPermissionMapper.updateAdminTeamPermission(teamId);
	}

	@Override
	public List<TeamPermissionResponse> findTeamPermissionsByTeamId(int teamId) {
		return teamPermissionMapper.findTeamPermissionsByTeamId(teamId);
	}

	@Override
	public void updateAvailableToTrue(TeamPermissionRequest teamPermissionRequest) {
		teamPermissionMapper.updateAvailableToTrue(teamPermissionRequest);
	}

	@Override
	public void updateAvailableToFalse(TeamPermissionRequest teamPermissionRequest) {
		teamPermissionMapper.updateAvailableToFalse(teamPermissionRequest);
	}

}
