package com.kms.byslboot.calendar.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.calendar.dto.CalendarResponse;
import com.kms.byslboot.calendar.entity.Calendar;

@Mapper
public interface CalendarMapper {
	
	public void insertCalendar(Calendar calendar);
	public Optional<CalendarResponse> findCalendarById(int calendarId);
	public List<CalendarResponse> findCalendarByDate(Calendar calendar);
	public List<CalendarResponse> findCalendarByMonth(Calendar calendar);
	public void updateCalendar(Calendar calendar);
	public void deleteCalendarById(int calendarId);
}
