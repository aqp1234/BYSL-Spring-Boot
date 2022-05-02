package com.kms.byslboot.dashboard.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.dashboard.dto.DashboardResponse;
import com.kms.byslboot.dashboard.entity.Dashboard;

@Mapper
public interface DashboardMapper {
	
	public void insertDashboard(Dashboard dashboard);
	public Optional<DashboardResponse> findDashboardById(int dashboardId);
	public List<DashboardResponse> findDashboardByWorkspaceId(int workspaceId);
	public void updateDashboard(Dashboard dashboard);
	public void deleteDashboardById(int dashboardId);
}
