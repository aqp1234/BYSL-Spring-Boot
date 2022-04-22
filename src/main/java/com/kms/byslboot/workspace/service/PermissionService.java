package com.kms.byslboot.workspace.service;

import java.util.List;

import com.kms.byslboot.workspace.entity.Permission;

public interface PermissionService {
	public List<Permission> findAllPermissions();
	public Permission findPermissionById(int permissionId);
}
