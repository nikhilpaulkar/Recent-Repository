package com.app.serviceIntf;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import com.app.dto.IRoleDetailDto;
import com.app.dto.IRoleListDto;
import com.app.dto.RoleDto;
import com.app.dto.RolePermissionDto;
import com.app.entities.RoleEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface RoleServiceInterface {

	Page<IRoleListDto> getAllRoles(String search, String from, String to);

	void addRole(RoleDto roleDto, Long userId);

	RoleEntity updateRole(RoleDto roleData, Long id, Long updateBy) throws ResourceNotFoundException;

	void deleteRole(Long id) throws ResourceNotFoundException;

	IRoleDetailDto getRoleById(Long id) throws ResourceNotFoundException;

	RolePermissionDto getRoleAndPermissionById(Long id) throws ResourceNotFoundException;

	ArrayList<String> getPermissionByUserId(Long userId);

	void addPermissionsToRole(Long id, Long[] permissions) throws ResourceNotFoundException;

}
