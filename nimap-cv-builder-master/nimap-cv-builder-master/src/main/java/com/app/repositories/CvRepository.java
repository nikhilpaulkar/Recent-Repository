package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.app.entities.CvRequestEntity;
import com.app.serviceIntf.ICvInfoMasterDto;

public interface CvRepository extends JpaRepository<CvRequestEntity, Long>
{
	Page<ICvInfoMasterDto> findByOrderByIdDesc(String name, Pageable pageable, Class<ICvInfoMasterDto> MasterListDto);

	Page<ICvInfoMasterDto> findByOrderByIdDesc(Pageable page, Class<ICvInfoMasterDto> MasterListDto);
	
	Optional<ICvInfoMasterDto> findByIdAndIsActiveTrue(Long id, Class<ICvInfoMasterDto> IMasterDetailDto);
	 
	 
	
}
