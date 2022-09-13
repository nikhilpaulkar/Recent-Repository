package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.ICvMasterDto;
import com.app.entities.CvRequestEntity;

import io.lettuce.core.dynamic.annotation.Param;

public interface CvRequestRepository extends JpaRepository<CvRequestEntity, Long>
{
	Page<ICvMasterDto> findByOrderByIdDesc(String name, Pageable pageable, Class<CvRequestEntity> MasterListDto);

	Page<ICvMasterDto> findByOrderByIdDesc(Pageable page, Class<ICvMasterDto> MasterListDto);
	
	@Query(value = "select u.* from cv_request u where u.user_id= :id",nativeQuery = true)
	Optional<CvRequestEntity> getuser(@Param("id") Long id);
	  
}
