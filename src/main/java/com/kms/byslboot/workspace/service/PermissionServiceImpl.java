package com.kms.byslboot.workspace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kms.byslboot.workspace.entity.Permission;
import com.kms.byslboot.workspace.exception.PermissionNotFoundException;
import com.kms.byslboot.workspace.mapper.PermissionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService{

	private final PermissionMapper permissionMapper;
	
	@Override
	public List<Permission> findAllPermissions() {
		return permissionMapper.findAllPermissions();
	}

	@Override
	public Permission findPermissionById(int permissionId) {
		return permissionMapper.findPermissionById(permissionId).orElseThrow(PermissionNotFoundException::new);
	}
	
}
