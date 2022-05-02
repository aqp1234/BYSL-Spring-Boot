package com.kms.byslboot.dashboard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kms.byslboot.dashboard.dto.DashboardRequest;
import com.kms.byslboot.dashboard.dto.DashboardResponse;
import com.kms.byslboot.dashboard.entity.Dashboard;
import com.kms.byslboot.dashboard.exception.DashboardNotFoundException;
import com.kms.byslboot.dashboard.mapper.DashboardMapper;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
	
	private final DashboardMapper dashboardMapper;
	private final UserWorkspaceService userWorkspaceService;

	@Override
	public int insertDashboard(DashboardRequest dashboardRequest, int workspaceId) {
		Dashboard dashboard = dashboardRequest.toEntity(dashboardRequest, workspaceId, userWorkspaceService);
		dashboardMapper.insertDashboard(dashboard);
		return dashboard.getId();
	}

	@Override
	public DashboardResponse findDashboardById(int dashboardId) {
		return dashboardMapper.findDashboardById(dashboardId).orElseThrow(DashboardNotFoundException::new);
	}

	@Override
	public List<DashboardResponse> findDashboardByWorkspaceId(int workspaceId) {
		return dashboardMapper.findDashboardByWorkspaceId(workspaceId);
	}

	@Override
	public void updateDashboard(DashboardRequest dashboardRequest, int dashboardId, int workspaceId) {
		Dashboard dashboard = dashboardRequest.toEntity(dashboardRequest, dashboardId, workspaceId, userWorkspaceService);
		dashboardMapper.updateDashboard(dashboard);
	}

	@Override
	public void deleteDashboardById(int dashboardId) {
		dashboardMapper.deleteDashboardById(dashboardId);
	}

}
