package com.kms.byslboot.workspace.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.workspace.entity.Workspace;

@Mapper
public interface WorkspaceMapper {
	int insertWorkspace(Workspace workspace);
	Optional<Workspace> findWorkspaceById(int workspaceId);
}
