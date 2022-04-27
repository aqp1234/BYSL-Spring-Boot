package com.kms.byslboot.workspace.service;

import java.util.List;

import com.kms.byslboot.workspace.dto.TeamPermissionRequest;
import com.kms.byslboot.workspace.dto.TeamPermissionResponse;

public interface TeamPermissionService {
	public void initTeamPermission(TeamPermissionRequest initTeamPermissionRequest);
	public void updateAdminTeamPermission(int teamId);
	public List<TeamPermissionResponse> findTeamPermissionsByTeamId(int teamId);
	public void updateAvailableToTrue(TeamPermissionRequest teamPermissionRequest);
	public void updateAvailableToFalse(TeamPermissionRequest teamPermissionRequest);
}
