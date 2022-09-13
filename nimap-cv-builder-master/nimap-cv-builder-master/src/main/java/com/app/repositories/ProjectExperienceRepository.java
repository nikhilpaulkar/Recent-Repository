package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dto.IProjectExperienceDto;
import com.app.entities.ProjectExperienceEntity;

public interface ProjectExperienceRepository extends JpaRepository<ProjectExperienceEntity, Long> {

	Page<IProjectExperienceDto> findByNameContainingIgnoreCaseAndUserIdIdAndIsActiveTrueOrderByIdDesc(String name, Long userId, Pageable pageable, Class<IProjectExperienceDto> IProjectExperienceDto);

	Page<IProjectExperienceDto> findByUserIdIdAndIsActiveTrueOrderByIdDesc(Long userId, Pageable page, Class<IProjectExperienceDto> IProjectExperienceDto);

	Optional<IProjectExperienceDto> findByIdAndUserIdId(Long id, Long userId, Class<IProjectExperienceDto> IProjectExperienceDto);

	Optional<ProjectExperienceEntity> findByIdAndUserIdId(Long id, Long userId);

	List<IProjectExperienceDto> findByUserIdIdAndIsActiveTrueOrderByCreatedAtDesc(Long userId, Class<IProjectExperienceDto> IProjectExperienceDto);

	List<ProjectExperienceEntity> findByUserIdIdAndIsActiveTrueOrderByCreatedAtDesc(Long userId);

	Optional<ProjectExperienceEntity> findByNameContainingIgnoreCase(String name);
}
