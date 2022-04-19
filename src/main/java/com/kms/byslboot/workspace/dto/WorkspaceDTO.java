package com.kms.byslboot.workspace.dto;

import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.kms.byslboot.workspace.entity.Workspace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceDTO {

	@NotBlank(message = "워크스페이스 이름은 빈 값일 수 없습니다.")
	@Size(max=40, message="워크스페이스 이름은 40자를 넘어갈 수 없습니다.")
	private String workspaceName;
	
	public Workspace toEntity(WorkspaceDTO workspaceDTO, HttpSession session) {
		return Workspace.builder()
				.workspaceName(workspaceDTO.getWorkspaceName())
				.ownerId((int) session.getAttribute(MEMBER_ID))
				.build();
	}
}
