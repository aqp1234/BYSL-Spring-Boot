package com.kms.byslboot.conference.controller;

import static com.kms.byslboot.common.ResponseEntityHttpStatus.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kms.byslboot.common.annotation.LoginRequired;
import com.kms.byslboot.conference.dto.ConferenceRequest;
import com.kms.byslboot.conference.dto.ConferenceResponse;
import com.kms.byslboot.conference.service.ConferenceService;

import lombok.RequiredArgsConstructor;

@LoginRequired
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conference/{workspaceId}")
public class ConferenceController {

	private final ConferenceService conferenceService;
	
	@GetMapping
	public ResponseEntity<List<ConferenceResponse>> findConferenceByWorkspaceId(@PathVariable int workspaceId){
		return ResponseEntity.status(200).body(conferenceService.findConferenceByWorkspaceId(workspaceId));
	}
	
	@PostMapping
	public ResponseEntity<ConferenceResponse> insertConference(@RequestBody ConferenceRequest conferenceRequest, @PathVariable int workspaceId) throws URISyntaxException{
		int conferenceId = conferenceService.insertConference(conferenceRequest, workspaceId);
		ConferenceResponse conferenceResponse = conferenceService.findConferenceById(conferenceId);
		return ResponseEntity.created(new URI("/conference/" + workspaceId + "/" + conferenceId))
				.header("Content-Location", "/api/conference/" + workspaceId + "/" + conferenceId)
				.body(conferenceResponse);
	}
	
	@GetMapping("/{conferenceId}")
	public ResponseEntity<ConferenceResponse> findConferenceById(@PathVariable int conferenceId){
		return ResponseEntity.ok(conferenceService.findConferenceById(conferenceId));
	}
	
	@PostMapping("/{conferenceId}")
	public ResponseEntity<HttpStatus> updateConference(@RequestBody ConferenceRequest conferenceRequest, @PathVariable int workspaceId
			, @PathVariable int conferenceId){
		conferenceService.updateConference(conferenceRequest, conferenceId, workspaceId);
		return RESPONSE_OK;
	}
	
	@DeleteMapping("/{conferenceId}")
	public ResponseEntity<HttpStatus> deleteConferenceById(@PathVariable int conferenceId){
		conferenceService.deleteConferenceById(conferenceId);
		return RESPONSE_OK;
	}
}
