package com.kms.byslboot.workspace.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.kms.byslboot.workspace.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
	
	@NotBlank(message = "팀 이름은 필수 값입니다.")
	@Size(max=20, message = "팀 이름은 20자를 넘을 수 없습니다.")
	private String name;
	
	public Team toEntity(TeamDTO teamDTO, int workspaceId) {
		return Team.builder()
				.workspaceId(workspaceId)
				.name(teamDTO.getName())
				.isAdmin(false)
				.isGuest(false)
				.build();
	}
	
	public Team toAdminEntity(TeamDTO teamDTO, int workspaceId) {
		return Team.builder()
				.workspaceId(workspaceId)
				.name(teamDTO.getName())
				.isAdmin(true)
				.isGuest(false)
				.build();
	}
	
	public Team toGuestEntity(TeamDTO teamDTO, int workspaceId) {
		return Team.builder()
				.workspaceId(workspaceId)
				.name(teamDTO.getName())
				.isAdmin(false)
				.isGuest(true)
				.build();
	}
}
