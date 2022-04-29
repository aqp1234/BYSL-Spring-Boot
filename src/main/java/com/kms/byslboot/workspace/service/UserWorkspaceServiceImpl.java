package com.kms.byslboot.workspace.service;

import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.kms.byslboot.workspace.dto.UserWorkspaceDTO;
import com.kms.byslboot.workspace.entity.UserWorkspace;
import com.kms.byslboot.workspace.exception.UserWorkspaceNotFoundException;
import com.kms.byslboot.workspace.mapper.UserWorkspaceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserWorkspaceServiceImpl implements UserWorkspaceService{
	
	private final UserWorkspaceMapper userWorkspaceMapper;
	private final HttpSession session;

	@Override
	public void insertUserWorkspace(UserWorkspaceDTO userWorkspaceDTO, int workspaceId, int teamId) {
		UserWorkspace userWorkspace = userWorkspaceDTO.toEntity(userWorkspaceDTO, session, workspaceId, teamId);
		userWorkspaceMapper.insertUserWorkspace(userWorkspace);
	}

	@Override
	public UserWorkspace findUserWorkspaceById(int userWorkspaceId) {
		return userWorkspaceMapper.findUserWorkspaceById(userWorkspaceId).orElseThrow(UserWorkspaceNotFoundException::new);
	}

	@Override
	public UserWorkspace findConnectedUserWorkspace(int workspaceId) {
		UserWorkspace userWorkspace = UserWorkspace.builder().userId((int) session.getAttribute(MEMBER_ID)).workspaceId(workspaceId).build();
		return userWorkspaceMapper.findConnectedUserWorkspace(userWorkspace).orElseThrow(UserWorkspaceNotFoundException::new);
	}

	@Override
	public List<UserWorkspace> findUserWorkspaceByWorkspaceId(int workspaceId) {
		return userWorkspaceMapper.findUserWorkspaceByWorkspaceId(workspaceId);
	}

	@Override
	public List<UserWorkspace> findUserWorkspaceByUserId(int userId) {
		return userWorkspaceMapper.findUserWorkspaceByUserId(userId);
	}

	@Override
	public void updateUserWorkspace(UserWorkspaceDTO userWorkspaceDTO, int userWorkspaceId) {
		UserWorkspace userWorkspace = userWorkspaceDTO.toUpdateEntity(userWorkspaceDTO, userWorkspaceId);
		userWorkspaceMapper.updateUserWorkspace(userWorkspace);
	}

	@Override
	public void deleteUserWorkspaceById(int userWorkspaceId) {
		userWorkspaceMapper.deleteUserWorkspaceById(userWorkspaceId);
	}

}
