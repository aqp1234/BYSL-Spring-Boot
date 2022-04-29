package com.kms.byslboot.workspace.entity;

import com.kms.byslboot.calendar.entity.Calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TeamPermission {
	
	private int id;
	private int teamId;
	private int permissionId;
	private boolean isAvailable;
}
