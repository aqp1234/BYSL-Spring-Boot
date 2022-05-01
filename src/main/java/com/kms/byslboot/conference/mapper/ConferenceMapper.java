package com.kms.byslboot.conference.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.conference.dto.ConferenceResponse;
import com.kms.byslboot.conference.entity.Conference;

@Mapper
public interface ConferenceMapper {
	
	public void insertConference(Conference conference);
	public Optional<ConferenceResponse> findConferenceById(int conferenceId);
	public List<ConferenceResponse> findConferenceByWorkspaceId(int workspaceId);
	public void updateConference(Conference conference);
	public void deleteConferenceById(int conferenceId);
}
