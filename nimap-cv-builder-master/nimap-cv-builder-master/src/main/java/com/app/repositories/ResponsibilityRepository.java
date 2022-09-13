package com.app.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dto.IResponsibilityDetailDto;
import com.app.entities.ResponsibilityMasterEntity;

public interface ResponsibilityRepository extends JpaRepository<ResponsibilityMasterEntity, Long> {

	Optional<ResponsibilityMasterEntity> findByIdAndIsActiveTrue(Long id);

	Page<IResponsibilityDetailDto> findByOrderByIdDesc(Pageable paging, Class<IResponsibilityDetailDto> class1);

	Page<IResponsibilityDetailDto> findByNameContainingIgnoreCaseOrCodeLanguagesNameContainingIgnoreCaseOrderByIdDesc(String search,String codingLang, Pageable paging, Class<IResponsibilityDetailDto> class1);

	Optional<IResponsibilityDetailDto> findById(Long id, Class<IResponsibilityDetailDto> class1);

	ArrayList<IResponsibilityDetailDto> findByCodeLanguagesIdInAndIsActiveTrueOrderByIdDesc(Long[] id, Class<IResponsibilityDetailDto> class1);

	Optional<ResponsibilityMasterEntity> findByNameContainingIgnoreCase(String name);
}
