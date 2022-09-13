package com.app.serviceIntf;

import com.app.dto.PermissionRequestDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface PermissionServiceInterface {

	void addPermission(PermissionRequestDto permissionBody);

	void editPermission(PermissionRequestDto permissionBody, Long permissionId) throws ResourceNotFoundException;

	void deletePermission(Long permissionId) throws ResourceNotFoundException;

}
