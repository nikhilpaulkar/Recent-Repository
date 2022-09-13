package com.app.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dto.IRoleDetailDto;
import com.app.dto.IRoleListDto;
import com.app.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	Page<IRoleListDto> findByIsActiveTrue(Pageable page, Class<IRoleListDto> IRoleListDto);

	Page<IRoleListDto> findByRoleNameContainingIgnoreCaseAndIsActiveTrue(String search, Pageable page,
			Class<IRoleListDto> IRoleListDto);

	Optional<IRoleDetailDto> findById(Long id, Class<IRoleDetailDto> IRoleDetailDto);

	ArrayList<RoleEntity> findByIsActiveTrue();

	Optional<RoleEntity> findByRoleNameContainingIgnoreCase(String name);
}
