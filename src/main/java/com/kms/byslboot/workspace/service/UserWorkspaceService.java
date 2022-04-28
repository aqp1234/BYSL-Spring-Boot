package com.kms.byslboot.workspace.service;

import java.util.List;
import java.util.Optional;

import com.kms.byslboot.workspace.dto.UserWorkspaceDTO;
import com.kms.byslboot.workspace.entity.UserWorkspace;

public interface UserWorkspaceService {
	public void insertUserWorkspace(UserWorkspaceDTO userWorkspaceDTO, int workspaceId, int teamId);
	public UserWorkspace findUserWorkspaceById(int userWorkspaceId);
	public UserWorkspace findConnectedUserWorkspace(UserWorkspaceDTO userWorkspaceDTO, int workspaceId);
	public List<UserWorkspace> findUserWorkspaceByWorkspaceId(int workspaceId);
	public List<UserWorkspace> findUserWorkspaceByUserId(int userId);
	public void updateUserWorkspace(UserWorkspaceDTO userWorkspaceDTO, int userWorkspaceId);
	public void deleteUserWorkspaceById(int userWorkspaceId);
}
