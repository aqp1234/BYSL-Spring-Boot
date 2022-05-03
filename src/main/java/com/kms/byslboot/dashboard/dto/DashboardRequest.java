package com.kms.byslboot.dashboard.dto;

import java.sql.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kms.byslboot.common.annotation.DateValid;
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
@DateValid(startDate = "startDate", endDate = "endDate")
public class DashboardRequest {
	
	@NotEmpty(message = "처리자 값은 필수 값입니다.")
	private int managerUserWorkspaceId;
	
	@NotEmpty(message = "제목은 빈 값일 수 없습니다.")
	@Size(max = 50, message = "제목은 50글자를 넘을 수 없습니다.")
	private String subject;
	
	@Size(max = 10000, message = "내용은 10000글자를 넘을 수 없습니다.")
	private String content;
	
	private String startDate;
	private String endDate;

	@NotEmpty(message = "처리 값은 빈 값일 수 없습니다.")
	@Max(value = 3, message = "처리 값은 1~3 사이여야 합니다.")
	@Min(value = 1, message = "처리 값은 1~3 사이여야 합니다.")
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
