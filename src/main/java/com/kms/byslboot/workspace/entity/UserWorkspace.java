package com.kms.byslboot.workspace.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkspace {
	
	private int id;
	private int userId;
	private int workspaceId;
	private int teamId;
	private String nick;
	private String color;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}
