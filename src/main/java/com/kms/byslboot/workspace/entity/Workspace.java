package com.kms.byslboot.workspace.entity;

import java.sql.Timestamp;

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
public class Workspace {
	
	private int id;
	private String workspaceName;
	private int ownerId;
	private Timestamp createdAt;
}
