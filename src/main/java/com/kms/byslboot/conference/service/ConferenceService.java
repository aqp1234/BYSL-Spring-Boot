package com.kms.byslboot.conference.service;

import java.util.List;

import com.kms.byslboot.conference.dto.ConferenceRequest;
import com.kms.byslboot.conference.dto.ConferenceResponse;

public interface ConferenceService {

	public int insertConference(ConferenceRequest conferenceRequest, int workspaceId);
	public ConferenceResponse findConferenceById(int conferenceId);
	public List<ConferenceResponse> findConferenceByWorkspaceId(int workspaceId);
	public void updateConference(ConferenceRequest conferenceRequest, int conferenceId, int workspaceId);
	public void deleteConferenceById(int conferenceId);
}
