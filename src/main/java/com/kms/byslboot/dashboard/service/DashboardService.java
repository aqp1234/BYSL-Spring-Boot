package com.kms.byslboot.dashboard.service;

import java.util.List;

import com.kms.byslboot.dashboard.dto.DashboardRequest;
import com.kms.byslboot.dashboard.dto.DashboardResponse;

public interface DashboardService {

	public int insertDashboard(DashboardRequest dashboardRequest, int workspaceId);
	public DashboardResponse findDashboardById(int dashboardId);
	public List<DashboardResponse> findDashboardByWorkspaceId(int workspaceId);
	public void updateDashboard(DashboardRequest dashboardRequest, int dashboardId, int workspaceId);
	public void deleteDashboardById(int dashboardId);
}
