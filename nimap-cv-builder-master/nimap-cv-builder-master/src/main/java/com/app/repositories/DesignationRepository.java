package com.app.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterListDto;
import com.app.entities.DesignationMasterEntity;

public interface DesignationRepository extends JpaRepository<DesignationMasterEntity, Long> {

	Page<MasterListDto> findByNameContainingIgnoreCaseOrderByIdDesc(String name, Pageable pageable, Class<MasterListDto> MasterListDto);

	Page<MasterListDto> findByOrderByIdDesc(Pageable page, Class<MasterListDto> MasterListDto);

	Optional<IMasterDetailDto> findByIdAndIsActiveTrue(Long id, Class<IMasterDetailDto> IMasterDetailDto);

	Optional<DesignationMasterEntity> findByIdAndIsActiveTrue(Long id);
	
	Optional<DesignationMasterEntity> findByNameContainingIgnoreCase(String name);

}
