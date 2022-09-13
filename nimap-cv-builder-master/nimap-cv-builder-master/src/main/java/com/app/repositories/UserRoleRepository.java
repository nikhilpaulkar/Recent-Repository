package com.app.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dto.IUserRoleDetailDto;
import com.app.dto.RoleIdListDto;
import com.app.entities.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

	ArrayList<RoleIdListDto> findByPkUserId(Long userId, Class<RoleIdListDto> RoleIdListDto);

	void deleteByPkUserId(Long userId);

	ArrayList<IUserRoleDetailDto> findByPkUserIdAndPkUserIsActiveTrue(Long userId, Class<IUserRoleDetailDto> IUserRoleDetailDto);

}
