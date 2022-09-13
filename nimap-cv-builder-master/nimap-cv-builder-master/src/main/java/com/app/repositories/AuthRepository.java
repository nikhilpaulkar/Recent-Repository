package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.UserEntity;

public interface AuthRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

}