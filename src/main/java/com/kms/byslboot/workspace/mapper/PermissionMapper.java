package com.kms.byslboot.workspace.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.kms.byslboot.workspace.entity.Permission;

@Mapper
public interface PermissionMapper {
	public List<Permission> findAllPermissions();
	public Optional<Permission> findPermissionById(int permissionId);
}
