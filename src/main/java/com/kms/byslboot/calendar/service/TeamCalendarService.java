package com.kms.byslboot.calendar.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.kms.byslboot.calendar.dto.CalendarRequest;
import com.kms.byslboot.calendar.dto.CalendarResponse;
import com.kms.byslboot.calendar.entity.Calendar;
import com.kms.byslboot.calendar.exception.CalendarNotFoundException;
import com.kms.byslboot.calendar.mapper.CalendarMapper;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamCalendarService implements CalendarService{
	
	private final CalendarMapper calendarMapper;
	private final UserWorkspaceService userWorkspaceService;

	@Override
	public int insertCalendar(CalendarRequest calendarRequest, int workspaceId) {
		Calendar calendar = calendarRequest.toEntity(calendarRequest, workspaceId, userWorkspaceService);
		calendarMapper.insertCalendar(calendar);
		return calendar.getId();
	}

	@Override
	public CalendarResponse findCalendarById(int calendarId) {
		return calendarMapper.findCalendarById(calendarId).orElseThrow(CalendarNotFoundException::new);
	}

	@Override
	public List<CalendarResponse> findCalendarByDate(CalendarRequest calendarRequest, int workspaceId) {
		Calendar calendar = Calendar.builder()
								.workspaceId(workspaceId)
								.startDate(Date.valueOf(calendarRequest.getStartDate()))
								.endDate(Date.valueOf(calendarRequest.getEndDate()))
								.build();
		return calendarMapper.findCalendarByDate(calendar);
	}

	@Override
	public List<CalendarResponse> findCalendarByMonth(CalendarRequest calendarRequest, int workspaceId) {
		Calendar calendar = Calendar.builder()
								.workspaceId(workspaceId)
								.startDate(Date.valueOf(calendarRequest.getStartDate()))
								.endDate(Date.valueOf(calendarRequest.getEndDate()))
								.build();
		return calendarMapper.findCalendarByMonth(calendar);
	}

	@Override
	public void updateCalendar(CalendarRequest calendarRequest, int calendarId, int workspaceId) {
		Calendar calendar = calendarRequest.toEntity(calendarRequest, calendarId, workspaceId, userWorkspaceService);
		calendarMapper.updateCalendar(calendar);
	}

	@Override
	public void deleteCalendarById(int calendarId) {
		calendarMapper.deleteCalendarById(calendarId);
	}

}
