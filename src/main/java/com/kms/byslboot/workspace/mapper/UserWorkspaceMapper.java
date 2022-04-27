package com.kms.byslboot.workspace.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.workspace.dto.UserWorkspaceDTO;
import com.kms.byslboot.workspace.entity.UserWorkspace;

@Mapper
public interface UserWorkspaceMapper {
	public void insertUserWorkspace(UserWorkspace userWorkspace);
	public Optional<UserWorkspace> findUserWorkspaceById(int userWorkspaceId);
	public List<UserWorkspace> findUserWorkspaceByWorkspaceId(int workspaceId);
	public List<UserWorkspace> findUserWorkspaceByUserId(int userId);
	public void updateUserWorkspace(UserWorkspace userWorkspace);
	public void deleteUserWorkspaceById(int userWorkspaceId);
}
