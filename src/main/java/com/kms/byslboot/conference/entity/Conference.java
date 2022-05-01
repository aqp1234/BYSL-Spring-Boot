package com.kms.byslboot.conference.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conference {
	
	private int id;
	private int workspaceId;
	private int ownerUserWorkspaceId;
	private String subject;
	private String content;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}
