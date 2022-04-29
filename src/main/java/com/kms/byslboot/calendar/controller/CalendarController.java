package com.kms.byslboot.calendar.controller;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kms.byslboot.calendar.dto.CalendarRequest;
import com.kms.byslboot.calendar.dto.CalendarResponse;
import com.kms.byslboot.calendar.service.CalendarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar/{workspaceId}")
public class CalendarController {
	
	private final CalendarService calendarService;
	
	@GetMapping
	public ResponseEntity<List<CalendarResponse>> findCalendarsByMonth(@RequestParam String startDate, @RequestParam String endDate
			, @PathVariable int workspaceId){
		List<CalendarResponse> calendars;
		CalendarRequest calendarRequest;
		
		calendarRequest = CalendarRequest.builder()
											.startDate(startDate)
											.endDate(endDate)
											.build();
		calendars = calendarService.findCalendarByMonth(calendarRequest, workspaceId);
		return ResponseEntity.status(200).body(calendars);
	}
	
	@PostMapping
	public ResponseEntity<CalendarResponse> insertCalendar(@RequestBody @Valid CalendarRequest calendarRequest, @PathVariable int workspaceId) throws URISyntaxException{
		calendarService.insertCalendar(calendarRequest, workspaceId);
		CalendarResponse calendarResponse = calendarService.findCalendarById(calendarRequest.getId());
		return ResponseEntity.created(new URI("/calendar/" + workspaceId + "/" + calendarResponse.getId()))
							.header("Content-Location", "/api/calenldar/" + workspaceId + "/" + calendarResponse.getId())
							.body(calendarResponse);
	}
	
	@GetMapping("/{calendarId}")
	public ResponseEntity<CalendarResponse> findCalendarById(@PathVariable int workspaceId, @PathVariable int calendarId){
		CalendarResponse calendarResponse = calendarService.findCalendarById(calendarId);
		return ResponseEntity.ok(calendarResponse);
	}
	
	@PostMapping("/{calendarId}")
	public ResponseEntity<HttpStatus> updateCalendar(@RequestBody @Valid CalendarRequest calendarRequest, @PathVariable int workspaceId){
		calendarService.updateCalendar(calendarRequest, workspaceId);
		return RESPONSE_OK;
	}
	
	@DeleteMapping("/{calendarId}")
	public ResponseEntity<HttpStatus> deleteCalendar(@PathVariable int workspaceId){
		calendarService.deleteCalendarById(workspaceId);
		return RESPONSE_OK;
	}
}
