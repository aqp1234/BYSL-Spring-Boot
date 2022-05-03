package com.kms.byslboot.share.dto;

import lombok.Getter;

@Getter
public class ShareResponse {
	
	private int id;
	private int workspaceId;
	private String workspaceName;
	private int ownerUserWorkspaceId;
	private String nick;
	private String color;
	private String subject;
	private String content;
}
