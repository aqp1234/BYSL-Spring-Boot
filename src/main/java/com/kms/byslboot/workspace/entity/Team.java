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
public class Team {
	
	private int id;
	private int workspaceId;
	private String name;
	private boolean isAdmin;
	private boolean isGuest;
}
