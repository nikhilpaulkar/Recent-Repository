package com.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.app.dto.IUserListDto;
import com.app.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmailAndIsActiveTrue(String email);

	Optional<UserEntity> findByIdAndIsActiveTrue(Long id);

	Page<IUserListDto> findByOrderByIdDesc(Pageable page, Class<IUserListDto> IUserListDto);
	
	Page<IUserListDto> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDesignationIdNameContainingIgnoreCaseOrderByIdDesc(String name, String email,String DesignationName, Pageable page, Class<IUserListDto> IUserListDto);
	
	List<IUserListDto> findByDateOfJoiningBetween(Date from,Date to);

	Optional<UserEntity> findByEmailContainingIgnoreCase(String email);
	//IUserRoleDetailDto findById(Long userId, Class<IUserRoleDetailDto> IUserRoleDetailDto);

	
}
