package com.kms.byslboot.calendar.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kms.byslboot.calendar.dto.CalendarRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar")
public class CalendarController {
	
	@PostMapping
	public ResponseEntity<HttpStatus> test(@RequestBody @Valid CalendarRequest calendarRequest){
		return ResponseEntity.ok(null);
	}
}
