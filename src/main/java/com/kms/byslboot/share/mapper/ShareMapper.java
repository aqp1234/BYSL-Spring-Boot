package com.kms.byslboot.share.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.share.dto.ShareResponse;
import com.kms.byslboot.share.entity.Share;

@Mapper
public interface ShareMapper {
	
	public void insertShare(Share share);
	public Optional<ShareResponse> findShareById(int shareId);
	public List<ShareResponse> findShareByWorkspaceId(int workspaceId);
	public void updateShare(Share share);
	public void deleteShareById(int shareId);
}
