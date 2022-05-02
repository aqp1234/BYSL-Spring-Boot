package com.kms.byslboot.dashboard.dto;

import java.sql.Date;

import com.kms.byslboot.dashboard.entity.Dashboard;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardRequest {
	
	private int ownerUserWorkspaceId;
	private int managerUserWorkspaceId;
	private String subject;
	private String content;
	private String startDate;
	private String endDate;
	private int flag;
	
	public Dashboard toEntity(DashboardRequest dashboardRequest, int workspaceId, UserWorkspaceService userWorkspaceService) {
		return Dashboard.builder()
					.workspaceId(workspaceId)
					.ownerUserWorkspaceId(userWorkspaceService.findConnectedUserWorkspace(workspaceId).getId())
					.managerUserWorkspaceId(dashboardRequest.getManagerUserWorkspaceId())
					.subject(dashboardRequest.getSubject())
					.content(dashboardRequest.getContent())
					.startDate(Date.valueOf(dashboardRequest.getStartDate()))
					.endDate(Date.valueOf(dashboardRequest.getEndDate()))
					.flag(dashboardRequest.getFlag())
					.build();
	}
	
	public Dashboard toEntity(DashboardRequest dashboardRequest, int dashboardId, int workspaceId, UserWorkspaceService userWorkspaceService) {
		return Dashboard.builder()
					.id(dashboardId)
					.workspaceId(workspaceId)
					.ownerUserWorkspaceId(userWorkspaceService.findConnectedUserWorkspace(workspaceId).getId())
					.managerUserWorkspaceId(dashboardRequest.getManagerUserWorkspaceId())
					.subject(dashboardRequest.getSubject())
					.content(dashboardRequest.getContent())
					.startDate(Date.valueOf(dashboardRequest.getStartDate()))
					.endDate(Date.valueOf(dashboardRequest.getEndDate()))
					.flag(dashboardRequest.getFlag())
					.build();
	}
}
