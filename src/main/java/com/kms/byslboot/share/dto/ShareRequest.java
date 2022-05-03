package com.kms.byslboot.share.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kms.byslboot.share.entity.Share;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareRequest {
	
	@NotEmpty(message = "제목은 빈 값일 수 없습니다.")
	@Size(max = 100, message = "제목은 100글자를 넘을 수 없습니다.")
	private String subject;
	
	@Size(max = 10000, message = "내용은 10000글자를 넘을 수 없습니다.")
	private String content;
	
	public Share toEntity(ShareRequest shareRequest, int workspaceId, UserWorkspaceService userWorkspaceService) {
		return Share.builder()
					.workspaceId(workspaceId)
					.ownerUserWorkspaceId(userWorkspaceService.findConnectedUserWorkspace(workspaceId).getId())
					.subject(shareRequest.getSubject())
					.content(shareRequest.getContent())
					.build();
	}
	
	public Share toEntity(ShareRequest shareRequest, int shareId, int workspaceId, UserWorkspaceService userWorkspaceService) {
		return Share.builder()
					.id(shareId)
					.workspaceId(workspaceId)
					.ownerUserWorkspaceId(userWorkspaceService.findConnectedUserWorkspace(workspaceId).getId())
					.subject(shareRequest.getSubject())
					.content(shareRequest.getContent())
					.build();
	}
}
