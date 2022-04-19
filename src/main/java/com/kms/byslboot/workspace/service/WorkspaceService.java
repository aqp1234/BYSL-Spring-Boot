package com.kms.byslboot.workspace.service;

import com.kms.byslboot.workspace.dto.WorkspaceDTO;
import com.kms.byslboot.workspace.entity.Workspace;

public interface WorkspaceService {
	int insertWorkspace(WorkspaceDTO workspaceDTO);
	Workspace findWorkspaceById(int workspaceId);
}
