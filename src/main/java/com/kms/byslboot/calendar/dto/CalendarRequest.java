package com.kms.byslboot.calendar.dto;

import java.sql.Date;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kms.byslboot.calendar.entity.Calendar;
import com.kms.byslboot.common.annotation.DateValid;
import com.kms.byslboot.workspace.entity.UserWorkspace;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DateValid
public class CalendarRequest {
	
	@NotEmpty(message = "제목은 빈 값일 수 없습니다.")
	@Size(max = 50, message = "제목은 50 글자를 넘을 수 없습니다.")
	private String subject;
	
	@Size(max = 100, message = "내용은 100 글자를 넘을 수 없습니다.")
	private String content;
	
	private String startDate;
	private String endDate;
	
	public Calendar toEntity(CalendarRequest calendarRequest, int workspaceId, UserWorkspaceService userWorkspaceService) {
		UserWorkspace userWorkspace = userWorkspaceService.findConnectedUserWorkspace(workspaceId);
		return Calendar.builder()
					.workspaceId(workspaceId)
					.ownerUserWorkspaceId(userWorkspace.getId())
					.subject(calendarRequest.getSubject())
					.content(calendarRequest.getContent())
					.startDate(Date.valueOf(calendarRequest.getStartDate()))
					.endDate(Date.valueOf(calendarRequest.getEndDate()))
					.build();
	}
	
	public Calendar toEntity(CalendarRequest calendarRequest, int calendarId, int workspaceId, UserWorkspaceService userWorkspaceService) {
		UserWorkspace userWorkspace = userWorkspaceService.findConnectedUserWorkspace(workspaceId);
		return Calendar.builder()
					.id(calendarId)
					.workspaceId(workspaceId)
					.ownerUserWorkspaceId(userWorkspace.getId())
					.subject(calendarRequest.getSubject())
					.content(calendarRequest.getContent())
					.startDate(Date.valueOf(calendarRequest.getStartDate()))
					.endDate(Date.valueOf(calendarRequest.getEndDate()))
					.build();
	}
}
