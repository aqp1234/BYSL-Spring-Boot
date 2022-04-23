package com.kms.byslboot.workspace.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamPermissionRequest {

	private int teamId;
	private List<Integer> availableIds;
}
