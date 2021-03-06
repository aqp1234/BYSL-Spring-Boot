package com.kms.byslboot.workspace.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.kms.byslboot.workspace.dto.WorkspaceDTO;
import com.kms.byslboot.workspace.entity.Workspace;
import com.kms.byslboot.workspace.exception.WorkspaceNotFoundException;
import com.kms.byslboot.workspace.mapper.WorkspaceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService{
	
	private final HttpSession session;
	private final WorkspaceMapper workspaceMapper;

	@Override
	public int insertWorkspace(WorkspaceDTO workspaceDTO) {
		Workspace workspace = workspaceDTO.toEntity(workspaceDTO, session);
		workspaceMapper.insertWorkspace(workspace);
		return workspace.getId();
	}

	@Override
	public Workspace findWorkspaceById(int workspaceId) {
		return workspaceMapper.findWorkspaceById(workspaceId).orElseThrow(WorkspaceNotFoundException::new);
	}

	@Override
	public void updateWorkspace(WorkspaceDTO workspaceDTO) {
		Workspace workspace = workspaceDTO.toEntity(workspaceDTO, session);
		workspaceMapper.updateWorkspace(workspace);
	}

	@Override
	public void deleteWorkspaceById(int workspaceId) {
		workspaceMapper.deleteWorkspaceById(workspaceId);
	}

}
