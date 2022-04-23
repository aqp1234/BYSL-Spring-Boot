package com.kms.byslboot.workspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamPermissionResponse {
	
	private int id;
	private int teamId;
	private int permissionId;
	private boolean isAvailable;
	private String codeName;
	private String name;
}
