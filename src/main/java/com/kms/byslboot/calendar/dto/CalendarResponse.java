package com.kms.byslboot.calendar.dto;

import java.sql.Date;

import lombok.Getter;

@Getter
public class CalendarResponse {
	
	private int id;
	private int workspaceId;
	private String workspaceName;
	private int ownerId;
	private String nick;
	private String color;
	private String subject;
	private String content;
	private Date startDate;
	private Date endDate;
}
