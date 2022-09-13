package com.app.serviceIntf;

import java.util.List;

import com.app.dto.EntityRequestDto;
import com.app.entities.EntityEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface EntityServiceInterface {

	void addEntity(EntityRequestDto entityDetail);

	List<EntityEntity> getAllEntity();

	void editEntity(EntityRequestDto entityDetail, Long entityId) throws ResourceNotFoundException;

	void deleteEntity(Long entityId) throws ResourceNotFoundException;

}
