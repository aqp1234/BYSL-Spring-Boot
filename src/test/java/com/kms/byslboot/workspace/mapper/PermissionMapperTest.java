package com.kms.byslboot.workspace.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.kms.byslboot.workspace.entity.Permission;
import com.kms.byslboot.workspace.exception.PermissionNotFoundException;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PermissionMapperTest {
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Test
	@DisplayName("모든 권한 리스트를 가져온다.")
	void findAllPermissions() {
		List<Permission> permissions = permissionMapper.findAllPermissions();
		assertThat(permissions.size()).isEqualTo(25);
		assertThat(permissions).anyMatch((permission) -> permission.getCodeName().equals("Add_Chat_Room"));
		assertThat(permissions).anyMatch((permission) -> permission.getCodeName().equals("Delete_Dashboard"));
		assertThat(permissions).anyMatch((permission) -> permission.getCodeName().equals("Can_Change_User_Group"));
	}
	
	@Test
	@DisplayName("특정 아이디의 권한을 가져온다.")
	void findPermissionById() {
		Permission permission = permissionMapper.findPermissionById(1).orElseThrow(PermissionNotFoundException::new);
		assertThat(permission.getCodeName()).isEqualTo("Add_Chat_Room");
		assertThat(permission.getName()).isEqualTo("Can add Chat Room");
	}
	
	@Test
	@DisplayName("없는 아이디를 조회할 시 PermissionNotFoundException 을 처리한다.")
	void findPermissionByIdFail() {
		assertThat(permissionMapper.findPermissionById(27)).isEqualTo(Optional.empty());
	}
}
