package com.kms.byslboot.dashboard.entity;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {
	
	private int id;
	private int workspaceId;
	private int ownerUserWorkspaceId;
	private int managerUserWorkspaceId;
	private String subject;
	private String content;
	private Date startDate;
	private Date endDate;
	private int flag;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}
