package com.kms.byslboot.share.mapper;

import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.mock.web.MockHttpSession;

import com.kms.byslboot.share.dto.ShareRequest;
import com.kms.byslboot.share.dto.ShareResponse;
import com.kms.byslboot.share.entity.Share;
import com.kms.byslboot.share.exception.ShareNotFoundException;
import com.kms.byslboot.workspace.entity.UserWorkspace;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ShareMapperTest {
	
	@Autowired
	private ShareMapper shareMapper;
	
	@Mock
	private UserWorkspaceService userWorkspaceService;
	
	private MockHttpSession session;
	private ShareRequest shareRequest;
	private Share share;
	private ShareRequest shareRequestUpdate;
	private Share shareUpdate;
	private UserWorkspace userWorkspace;

	@BeforeEach
	void setUp() throws Exception {
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
		
		shareRequest = ShareRequest.builder()
								.subject("subjectTest")
								.content("contentTest")
								.build();
		share = shareRequest.toEntity(shareRequest, userWorkspace.getWorkspaceId(), userWorkspaceService);
	}

	@Test
	@DisplayName("자료 공유 추가 테스트")
	void insertShare() {
		shareMapper.insertShare(share);
		assertThat(shareMapper.findShareById(share.getId())).isNotEmpty();
	}
	
	@Test
	@DisplayName("자료 공유 검색 테스트")
	void findShareById() {
		shareMapper.insertShare(share);
		ShareResponse shareResponse = shareMapper.findShareById(share.getId()).orElseThrow(ShareNotFoundException::new);
		assertThat(shareResponse.getSubject()).isEqualTo(share.getSubject());
		assertThat(shareResponse.getContent()).isEqualTo(share.getContent());
		assertThat(shareResponse.getOwnerUserWorkspaceId()).isEqualTo(userWorkspace.getId());
	}
	
	@Test
	@DisplayName("자료 공유 리스트 검색 테스트")
	void findShareByWorkspaceId() {
		shareMapper.insertShare(share);
		shareMapper.insertShare(share);
		assertThat(shareMapper.findShareByWorkspaceId(userWorkspace.getWorkspaceId()).size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("자료 공유 업데이트 테스트")
	void updateShare() {
		shareMapper.insertShare(share);
		shareRequestUpdate = ShareRequest.builder()
									.subject("subjectUpdate")
									.content("contentUpdate")
									.build();
		shareUpdate = shareRequestUpdate.toEntity(shareRequest, share.getId(), userWorkspace.getWorkspaceId(), userWorkspaceService);
		shareMapper.updateShare(shareUpdate);
		ShareResponse shareResponse = shareMapper.findShareById(share.getId()).orElseThrow(ShareNotFoundException::new);
		assertThat(shareResponse.getContent()).isEqualTo(shareUpdate.getContent());
		assertThat(shareResponse.getSubject()).isEqualTo(shareUpdate.getSubject());
	}
	
	@Test
	@DisplayName("자료 공유 삭제 테스트")
	void deleteShare() {
		shareMapper.insertShare(share);
		shareMapper.deleteShareById(share.getId());
		assertThat(shareMapper.findShareById(share.getId())).isEmpty();
	}
}
