package com.kms.byslboot.workspace.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.workspace.entity.Workspace;

@Mapper
public interface WorkspaceMapper {
	public void insertWorkspace(Workspace workspace);
	public Optional<Workspace> findWorkspaceById(int workspaceId);
	public void updateWorkspace(Workspace workspace);
	public void deleteWorkspaceById(int workspaceId);
}
