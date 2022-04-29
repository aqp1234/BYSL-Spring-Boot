package com.kms.byslboot.calendar.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Calendar {
	
	private int id;
	private int workspaceId;
	private int ownerUserWorkspaceId;
	private String subject;
	private String content;
	private Date startDate;
	private Date endDate;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}
