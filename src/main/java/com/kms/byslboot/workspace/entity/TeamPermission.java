package com.kms.byslboot.workspace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamPermission {
	
	private int id;
	private int teamId;
	private int permissionId;
	private boolean isAvailable;
}
