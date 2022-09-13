package com.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.EntityRequestDto;
import com.app.entities.EntityEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.EntityRepository;
import com.app.serviceIntf.EntityServiceInterface;

@Service
public class EntityServiceImpl implements EntityServiceInterface {

	@Autowired
	EntityRepository entityRepository;

	public EntityServiceImpl() {

		// TODO Auto-generated constructor stub
	}

	@Override
	public void addEntity(EntityRequestDto entityDetail) {

		EntityEntity newEntity = new EntityEntity();
		newEntity.setEntityName(entityDetail.getEntityName());
		newEntity.setDescription(entityDetail.getDescription());
		entityRepository.save(newEntity);
		return;

	}

	@Override
	public List<EntityEntity> getAllEntity() {

		return entityRepository.findAllByIsActiveTrue();

	}

	@Override
	public void editEntity(EntityRequestDto entityDetail, Long entityId) throws ResourceNotFoundException {

		EntityEntity entityEntity = entityRepository.findById(entityId).orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		entityEntity.setEntityName(entityDetail.getEntityName());
		entityEntity.setDescription(entityDetail.getDescription());
		entityRepository.save(entityEntity);
		return;

	}

	@Override
	public void deleteEntity(Long entityId) throws ResourceNotFoundException {

		EntityEntity entityEntity = entityRepository.findById(entityId).orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		entityEntity.setIsActive(false);
		entityRepository.save(entityEntity);
		return;

	}

}
