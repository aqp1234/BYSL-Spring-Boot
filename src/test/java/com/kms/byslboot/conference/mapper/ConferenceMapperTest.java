package com.kms.byslboot.conference.mapper;

import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.mock.web.MockHttpSession;

import com.kms.byslboot.conference.dto.ConferenceRequest;
import com.kms.byslboot.conference.dto.ConferenceResponse;
import com.kms.byslboot.conference.entity.Conference;
import com.kms.byslboot.conference.exception.ConferenceNotFoundException;
import com.kms.byslboot.workspace.entity.UserWorkspace;
import com.kms.byslboot.workspace.service.UserWorkspaceServiceImpl;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ConferenceMapperTest {
	
	@Autowired
	private ConferenceMapper conferenceMapper;
	
	@Mock
	private UserWorkspaceServiceImpl userWorkspaceService;

	private MockHttpSession session;
	private ConferenceRequest conferenceRequest;
	private Conference conference;
	private UserWorkspace userWorkspace;
	
	@BeforeEach
	void setup() {
		session = new MockHttpSession();
		session.setAttribute(MEMBER_ID, 11);
		
		userWorkspace = UserWorkspace.builder()
							.id(2)
							.userId(11)
							.workspaceId(3)
							.teamId(49)
							.nick("김민석")
							.color("#ffa3a3")
							.build();
		
		when(userWorkspaceService.findConnectedUserWorkspace(any(int.class))).thenReturn(userWorkspace);
		
		conferenceRequest = ConferenceRequest.builder()
								.subject("subjectTest")
								.content("contentTest")
								.build();
		
		conference = conferenceRequest.toEntity(conferenceRequest, userWorkspace.getWorkspaceId(), userWorkspaceService);
	}
	
	@Test
	@DisplayName("회의록 추가 테스트")
	void insertConferenceTest() {
		conferenceMapper.insertConference(conference);
		ConferenceResponse conferenceResponse = conferenceMapper.findConferenceById(conference.getId()).orElseThrow(ConferenceNotFoundException::new);
		assertThat(conferenceResponse.getSubject()).isEqualTo(conferenceRequest.getSubject());
		assertThat(conferenceResponse.getContent()).isEqualTo(conferenceRequest.getContent());
	}
	
	@Test
	@DisplayName("회의록 리스트 가져오기 테스트")
	void findConferenceByWorkspaceId() {
		conferenceMapper.insertConference(conference);
		conferenceMapper.insertConference(conference);
		List<ConferenceResponse> conferenceResponses = conferenceMapper.findConferenceByWorkspaceId(conference.getWorkspaceId());
		assertThat(conferenceResponses.size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("회의록 업데이트 테스트")
	void updateConference() {
		conferenceMapper.insertConference(conference);
		System.out.println(conference.getId());
		Conference conferenceUpdate = Conference.builder()
											.id(conference.getId())
											.subject("subjectUpdate")
											.content("contentUpdate")
											.build();
		conferenceMapper.updateConference(conferenceUpdate);
		ConferenceResponse conferenceResponse = conferenceMapper.findConferenceById(conference.getId()).orElseThrow(ConferenceNotFoundException::new);
		assertThat(conferenceResponse.getSubject()).isEqualTo(conferenceUpdate.getSubject());
		assertThat(conferenceResponse.getContent()).isEqualTo(conferenceUpdate.getContent());
	}
	
	@Test
	@DisplayName("회의록 삭제 테스트")
	void deleteConference() {
		conferenceMapper.insertConference(conference);
		conferenceMapper.deleteConferenceById(conference.getId());
		assertThat(conferenceMapper.findConferenceById(conference.getId())).isEmpty();
	}
}
