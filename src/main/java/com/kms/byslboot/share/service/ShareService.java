package com.kms.byslboot.share.service;

import java.util.List;

import com.kms.byslboot.share.dto.ShareRequest;
import com.kms.byslboot.share.dto.ShareResponse;

public interface ShareService {
	
	public int insertShare(ShareRequest shareRequest, int workspaceId);
	public ShareResponse findShareById(int shareId);
	public List<ShareResponse> findShareByWorkspaceId(int workspaceId);
	public void updateShare(ShareRequest shareRequest, int shareId, int workspaceId);
	public void deleteShareById(int shareId);
}
