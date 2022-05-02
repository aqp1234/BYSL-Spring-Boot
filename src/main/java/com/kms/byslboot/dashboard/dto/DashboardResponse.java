package com.kms.byslboot.dashboard.dto;

import java.sql.Date;

import lombok.Getter;

@Getter
public class DashboardResponse {
	
	private int id;
	private int workspaceId;
	private String workspaceName;
	private int ownerUserWorkspaceId;
	private String nick;
	private String color;
	private int managerUserWorkspaceId;
	private String managerNick;
	private String managerColor;
	private String subject;
	private String content;
	private Date startDate;
	private Date endDate;
	private int flag;
}
