package com.kms.byslboot.calendar.service;

import java.util.List;
import java.util.Optional;

import com.kms.byslboot.calendar.dto.CalendarRequest;
import com.kms.byslboot.calendar.dto.CalendarResponse;

public interface CalendarService {

	public int insertCalendar(CalendarRequest calendarRequest, int workspaceId);
	public CalendarResponse findCalendarById(int calendarId);
	public List<CalendarResponse> findCalendarByDate(CalendarRequest calendarRequest, int workspaceId);
	public List<CalendarResponse> findCalendarByMonth(CalendarRequest calendarRequest, int workspaceId);
	public void updateCalendar(CalendarRequest calendarRequest, int calendarId, int workspaceId);
	public void deleteCalendarById(int calendarId);
}
