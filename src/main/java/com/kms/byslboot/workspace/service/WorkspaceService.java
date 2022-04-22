package com.kms.byslboot.workspace.service;

import com.kms.byslboot.workspace.dto.WorkspaceDTO;
import com.kms.byslboot.workspace.entity.Workspace;

public interface WorkspaceService {
	public int insertWorkspace(WorkspaceDTO workspaceDTO);
	public Workspace findWorkspaceById(int workspaceId);
}
