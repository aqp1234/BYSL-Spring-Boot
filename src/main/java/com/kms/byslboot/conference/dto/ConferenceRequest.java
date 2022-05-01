package com.kms.byslboot.conference.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kms.byslboot.conference.entity.Conference;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceRequest {
	
	@NotEmpty(message = "제목은 빈 값일 수 없습니다.")
	@Size(max = 100, message = "제목은 100 글자를 넘을 수 없습니다.")
	private String subject;
	
	@Size(max = 10000, message = "내용은 10000 글자를 넘을 수 없습니다.")
	private String content;
	
	public Conference toEntity(ConferenceRequest conferenceRequest, int workspaceId, UserWorkspaceService userWorkspaceService) {
		return Conference.builder()
				.workspaceId(workspaceId)
				.ownerUserWorkspaceId(userWorkspaceService.findConnectedUserWorkspace(workspaceId).getId())
				.subject(conferenceRequest.getSubject())
				.content(conferenceRequest.getContent())
				.build();
	}
	
	public Conference toEntity(ConferenceRequest conferenceRequest, int conferenceId, int workspaceId, UserWorkspaceService userWorkspaceService) {
		return Conference.builder()
				.id(conferenceId)
				.workspaceId(workspaceId)
				.ownerUserWorkspaceId(userWorkspaceService.findConnectedUserWorkspace(workspaceId).getId())
				.subject(conferenceRequest.getSubject())
				.content(conferenceRequest.getContent())
				.build();
	}
}
