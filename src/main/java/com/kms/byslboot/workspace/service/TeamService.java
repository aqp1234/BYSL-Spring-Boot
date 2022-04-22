package com.kms.byslboot.workspace.service;

import java.util.List;

import com.kms.byslboot.workspace.dto.TeamDTO;
import com.kms.byslboot.workspace.entity.Team;

public interface TeamService {
	public int teamInsert(TeamDTO teamDTO, int workspaceId);
	public int adminTeamInsert(TeamDTO teamDTO, int workspaceId);
	public int guestTeamInsert(TeamDTO teamDTO, int workspaceId);
	public Team teamSelectById(int teamId);
	public Team adminTeamSelectByWorkspaceId(int workspaceId);
	public Team guestTeamSelectByWorkspaceId(int workspaceId);
	public List<Team> allTeamSelectByWorkspaceId(int workspaceId);
	void teamDeleteById(int teamId);
}
