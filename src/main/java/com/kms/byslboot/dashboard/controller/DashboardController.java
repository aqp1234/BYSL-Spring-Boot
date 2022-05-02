package com.kms.byslboot.dashboard.controller;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kms.byslboot.common.annotation.LoginRequired;
import com.kms.byslboot.dashboard.dto.DashboardRequest;
import com.kms.byslboot.dashboard.dto.DashboardResponse;
import com.kms.byslboot.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

@LoginRequired
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard/{workspaceId}")
public class DashboardController {
	
	private DashboardService dashboardService;
	
	@GetMapping
	public ResponseEntity<List<DashboardResponse>> findDashboardByWorkspaecId(@PathVariable int workspaceId){
		List<DashboardResponse> dashboards = dashboardService.findDashboardByWorkspaceId(workspaceId);
		return ResponseEntity.ok(dashboards);
	}
	
	@PostMapping
	public ResponseEntity<DashboardResponse> insertDashboard(@RequestBody DashboardRequest dashboardRequest, @PathVariable int workspaceId) throws URISyntaxException{
		int dashboardId = dashboardService.insertDashboard(dashboardRequest, workspaceId);
		DashboardResponse dashboardResponse = dashboardService.findDashboardById(dashboardId);
		return ResponseEntity.created(new URI("/dashboard/" + workspaceId + "/" + dashboardId))
				.header("Content-Location", "/api/dashboard/" + workspaceId + "/" + dashboardId)
				.body(dashboardResponse);
	}
	
	@GetMapping("/{dashboardId}")
	public ResponseEntity<DashboardResponse> findDashboardById(@PathVariable int dashboardId){
		DashboardResponse dashboardResponse = dashboardService.findDashboardById(dashboardId);
		return ResponseEntity.ok(dashboardResponse);
	}
	
	@PostMapping("/{dashboardId}")
	public ResponseEntity<HttpStatus> updateDashboard(@RequestBody DashboardRequest dashboardRequest, @PathVariable int workspaceId
			, @PathVariable int dashboardId){
		dashboardService.updateDashboard(dashboardRequest, dashboardId, workspaceId);
		return RESPONSE_OK;
	}
	
	@DeleteMapping("/{dashboardId}")
	public ResponseEntity<HttpStatus> deleteDashboardById(@PathVariable int dashboardId){
		dashboardService.deleteDashboardById(dashboardId);
		return RESPONSE_OK;
	}
}
