package com.app.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterListDto;
import com.app.entities.CodingLanguageMasterEntity;

public interface CodingLanguageRepository extends JpaRepository<CodingLanguageMasterEntity, Long> {

	Page<MasterListDto> findByNameContainingIgnoreCaseOrderByIdDesc(String name, Pageable pageable, Class<MasterListDto> MasterListDto);

	Page<MasterListDto> findByOrderByIdDesc(Pageable page, Class<MasterListDto> MasterListDto);

	Optional<IMasterDetailDto> findByIdAndIsActiveTrue(Long id, Class<IMasterDetailDto> IMasterDetailDto);

	Optional<CodingLanguageMasterEntity> findByIdAndIsActiveTrue(Long id);

	ArrayList<MasterListDto> findByIdIn(ArrayList<Long> frameworkId, Class<MasterListDto> MasterListDto);

	List<MasterListDto> findByIsActiveTrue(Class<MasterListDto> MasterListDto);

	Optional<CodingLanguageMasterEntity> findByNameContainingIgnoreCase(String name);
}
