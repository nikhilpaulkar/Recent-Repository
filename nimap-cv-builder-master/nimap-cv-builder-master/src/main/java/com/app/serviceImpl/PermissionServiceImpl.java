package com.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.PermissionRequestDto;
import com.app.entities.PermissionEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.EntityRepository;
import com.app.repositories.PermissionRepository;
import com.app.serviceIntf.PermissionServiceInterface;

@Service
public class PermissionServiceImpl implements PermissionServiceInterface {

	public PermissionServiceImpl() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Override
	public void addPermission(PermissionRequestDto permissionBody) {

		PermissionEntity newPermission = new PermissionEntity();
		newPermission.setActionName(permissionBody.getActionName());
		newPermission.setBaseUrl(permissionBody.getBaseUrl());
		newPermission.setDescription(permissionBody.getDescription());
		newPermission.setEntityId(entityRepository.getById(permissionBody.getEntityId()));
		newPermission.setMethod(permissionBody.getMethod());
		newPermission.setPath(permissionBody.getPath());
		permissionRepository.save(newPermission);
		return;

	}

	@Override
	public void editPermission(PermissionRequestDto permissionBody, Long permissionId) throws ResourceNotFoundException {

		PermissionEntity permissionData = permissionRepository.findById(permissionId).orElseThrow(() -> new ResourceNotFoundException("Permission Not Found"));
		permissionData.setActionName(permissionBody.getActionName());
		permissionData.setBaseUrl(permissionBody.getBaseUrl());
		permissionData.setDescription(permissionBody.getDescription());
		permissionData.setEntityId(entityRepository.getById(permissionBody.getEntityId()));
		permissionData.setMethod(permissionBody.getMethod());
		permissionData.setPath(permissionBody.getPath());
		permissionRepository.save(permissionData);
		return;

	}

	@Override
	public void deletePermission(Long permissionId) throws ResourceNotFoundException {

		PermissionEntity permissionData = permissionRepository.findById(permissionId).orElseThrow(() -> new ResourceNotFoundException("Permission Not Found"));
		permissionData.setIsActive(false);
		permissionRepository.save(permissionData);
		return;

	}

}
