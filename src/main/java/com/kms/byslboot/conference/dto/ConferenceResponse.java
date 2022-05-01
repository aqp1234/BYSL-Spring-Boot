package com.kms.byslboot.conference.dto;

import lombok.Getter;

@Getter
public class ConferenceResponse {
	
	private int id;
	private int workspaceId;
	private int workspaceName;
	private int ownerId;
	private String nick;
	private String color;
	private String subject;
	private String content;
}
