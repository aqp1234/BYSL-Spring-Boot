package com.kms.byslboot.share.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kms.byslboot.share.dto.ShareRequest;
import com.kms.byslboot.share.dto.ShareResponse;
import com.kms.byslboot.share.entity.Share;
import com.kms.byslboot.share.exception.ShareNotFoundException;
import com.kms.byslboot.share.mapper.ShareMapper;
import com.kms.byslboot.workspace.service.UserWorkspaceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService{
	
	private final ShareMapper shareMapper;
	private final UserWorkspaceService userWorkspaceService;
	
	@Override
	public int insertShare(ShareRequest shareRequest, int workspaceId) {
		Share share = shareRequest.toEntity(shareRequest, workspaceId, userWorkspaceService);
		shareMapper.insertShare(share);
		return share.getId();
	}

	@Override
	public ShareResponse findShareById(int shareId) {
		return shareMapper.findShareById(shareId).orElseThrow(ShareNotFoundException::new);
	}

	@Override
	public List<ShareResponse> findShareByWorkspaceId(int workspaceId) {
		return shareMapper.findShareByWorkspaceId(workspaceId);
	}

	@Override
	public void updateShare(ShareRequest shareRequest, int shareId, int workspaceId) {
		Share share = shareRequest.toEntity(shareRequest, shareId, workspaceId, userWorkspaceService);
		shareMapper.updateShare(share);
	}

	@Override
	public void deleteShareById(int shareId) {
		shareMapper.deleteShareById(shareId);
	}
}
