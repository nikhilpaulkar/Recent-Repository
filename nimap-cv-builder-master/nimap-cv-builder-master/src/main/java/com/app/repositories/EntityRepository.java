package com.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.EntityEntity;

public interface EntityRepository extends JpaRepository<EntityEntity, Long> {

	List<EntityEntity> findAllByIsActiveTrue();
	
}
