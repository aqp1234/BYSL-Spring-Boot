package com.kms.byslboot.workspace.controller;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.RESPONSE_OK;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kms.byslboot.workspace.dto.TeamDTO;
import com.kms.byslboot.workspace.dto.TeamPermissionRequest;
import com.kms.byslboot.workspace.dto.UserWorkspaceDTO;
import com.kms.byslboot.workspace.dto.WorkspaceDTO;
import com.kms.byslboot.workspace.entity.Permission;
import com.kms.byslboot.workspace.entity.Workspace;
import com.kms.byslboot.workspace.service.PermissionService;
import com.kms.byslboot.workspace.service.TeamPermissionService;
import com.kms.byslboot.workspace.service.TeamService;
import com.kms.byslboot.workspace.service.UserWorkspaceService;
import com.kms.byslboot.workspace.service.WorkspaceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workspace")
public class WorkspaceController {
	
	private final WorkspaceService workspaceService;
	private final TeamService teamService;
	private final TeamPermissionService teamPermissionService;
	private final PermissionService permissionService;
	private final UserWorkspaceService userWorkspaceService;
	
	@PostMapping
	public ResponseEntity<HttpStatus> insertWorkspace(@RequestBody @Valid WorkspaceDTO workspaceDTO, 
			@RequestBody @Valid UserWorkspaceDTO userWorkspaceDTO){
		int workspaceId;
		int adminTeamId;
		int guestTeamId;
		TeamDTO adminTeamDTO;
		TeamDTO guestTeamDTO;
		TeamPermissionRequest adminTeamPermissionRequest;
		TeamPermissionRequest guestTeamPermissionRequest;
		
		workspaceId = workspaceService.insertWorkspace(workspaceDTO);
		
		adminTeamDTO = new TeamDTO("Admin");
		guestTeamDTO = new TeamDTO("Guest");
		adminTeamId = teamService.adminTeamInsert(adminTeamDTO, workspaceId);
		guestTeamId = teamService.guestTeamInsert(guestTeamDTO, workspaceId);
		
		List<Integer> availableIds = new ArrayList<>();
		List<Permission> permissions = permissionService.findAllPermissions();
		for(Permission permission : permissions) {
			availableIds.add(permission.getId());
		}
		adminTeamPermissionRequest = TeamPermissionRequest.builder()
									.teamId(adminTeamId)
									.availableIds(availableIds)
									.build();
		guestTeamPermissionRequest = TeamPermissionRequest.builder()
									.teamId(guestTeamId)
									.availableIds(availableIds)
									.build();
		teamPermissionService.initTeamPermission(adminTeamPermissionRequest);
		teamPermissionService.updateAdminTeamPermission(adminTeamPermissionRequest.getTeamId());
		teamPermissionService.initTeamPermission(guestTeamPermissionRequest);
		
		userWorkspaceService.insertUserWorkspace(userWorkspaceDTO, workspaceId, guestTeamId);
		
		return RESPONSE_OK;
	}
	
	@GetMapping("/{workspaceId}")
	public ResponseEntity<Workspace> findWorkspaceById(@PathVariable int workspaceId){
		return ResponseEntity.status(200).body(workspaceService.findWorkspaceById(workspaceId));
	}
	
	@PostMapping("/{workspaceId}")
	public ResponseEntity<HttpStatus> updateWorkspace(@RequestBody WorkspaceDTO workspaceDTO){
		workspaceService.updateWorkspace(workspaceDTO);
		return RESPONSE_OK;
	}
	
	@DeleteMapping("/{workspaceId}")
	public ResponseEntity<HttpStatus> deleteWorkspaceById(@PathVariable int workspaceId){
		workspaceService.deleteWorkspaceById(workspaceId);
		return RESPONSE_OK;
	}
}
