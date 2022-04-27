package com.kms.byslboot.workspace.dto;

import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.kms.byslboot.workspace.entity.UserWorkspace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkspaceDTO {
	
	@NotBlank(message = "닉네임은 필수 값입니다.")
	@Size(max = 20, message = "닉네임은 20글자를 넘어갈 수 없습니다.")
	private String nick;
	
	@NotBlank(message = "색상은 필수 값입니다.")
	private String color;
	
	public UserWorkspace toEntity(UserWorkspaceDTO userWorkspaceDTO, HttpSession session, int workspaceId, int teamId) {
		return UserWorkspace.builder()
					.userId((int) session.getAttribute(MEMBER_ID))
					.workspaceId(workspaceId)
					.teamId(teamId)
					.nick(userWorkspaceDTO.getNick())
					.color(userWorkspaceDTO.getColor())
					.build();
	}
	
	public UserWorkspace toUpdateEntity(UserWorkspaceDTO userWorkspaceDTO, int userWorkspaceId) {
		return UserWorkspace.builder()
					.id(userWorkspaceId)
					.nick(userWorkspaceDTO.getNick())
					.color(userWorkspaceDTO.getColor())
					.build();
	}
}
