package com.kms.byslboot.workspace.service;

import java.util.List;
import java.util.Optional;

import com.kms.byslboot.workspace.dto.UserWorkspaceDTO;
import com.kms.byslboot.workspace.entity.UserWorkspace;

public interface UserWorkspaceService {
	public void insertUserWorkspace(UserWorkspaceDTO userWorkspaceDTO, int workspaceId, int teamId);
	public UserWorkspace findUserWorkspaceById(int userWorkspaceId);
	public List<UserWorkspace> findUserWorkspaceByWorkspaceId(int workspaceId);
	public void updateUserWorkspace(UserWorkspaceDTO userWorkspaceDTO, int userWorkspaceId);
	public void deleteUserWorkspaceById(int userWorkspaceId);
}