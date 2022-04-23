package com.kms.byslboot.workspace.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.workspace.dto.TeamPermissionRequest;
import com.kms.byslboot.workspace.dto.TeamPermissionResponse;

@Mapper
public interface TeamPermissionMapper {
	public void initTeamPermission(TeamPermissionRequest initTeamPermissionRequest);
	public void updateAdminTeamPermission(int teamId);
	public List<TeamPermissionResponse> findTeamPermissionsByTeamId(int teamId);
	public void updateAvailableToTrue(TeamPermissionRequest teamPermissionRequest);
	public void updateAvailableToFalse(TeamPermissionRequest teamPermissionRequest);
}
