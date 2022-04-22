package com.kms.byslboot.workspace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
	
	private int id;
	private int workspaceId;
	private String name;
	private boolean isAdmin;
	private boolean isGuest;
}
