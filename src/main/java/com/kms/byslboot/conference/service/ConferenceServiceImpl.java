package com.kms.byslboot.conference.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kms.byslboot.conference.dto.ConferenceRequest;
import com.kms.byslboot.conference.dto.ConferenceResponse;
import com.kms.byslboot.conference.entity.Conference;
import com.kms.byslboot.conference.exception.ConferenceNotFoundException;
import com.kms.byslboot.conference.mapper.ConferenceMapper;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService{

	private final ConferenceMapper conferenceMapper;
	private final UserWorkspaceService userWorkspaceService;
	
	@Override
	public int insertConference(ConferenceRequest conferenceRequest, int workspaceId) {
		Conference conference = conferenceRequest.toEntity(conferenceRequest, workspaceId, userWorkspaceService);
		conferenceMapper.insertConference(conference);
		return conference.getId();
	}

	@Override
	public ConferenceResponse findConferenceById(int conferenceId) {
		return conferenceMapper.findConferenceById(conferenceId).orElseThrow(ConferenceNotFoundException::new);
	}

	@Override
	public List<ConferenceResponse> findConferenceByWorkspaceId(int workspaceId) {
		return conferenceMapper.findConferenceByWorkspaceId(workspaceId);
	}

	@Override
	public void updateConference(ConferenceRequest conferenceRequest, int conferenceId, int workspaceId) {
		Conference conference = conferenceRequest.toEntity(conferenceRequest, conferenceId, workspaceId, userWorkspaceService);
		conferenceMapper.updateConference(conference);
	}

	@Override
	public void deleteConferenceById(int conferenceId) {
		conferenceMapper.deleteConferenceById(conferenceId);
	}

}
