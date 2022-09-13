package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.LoggerEntity;

public interface LoggerRepository extends JpaRepository<LoggerEntity, Long> {
	// @Query("SELECT c FROM LoggerEntity c WHERE c.token = :token")
	// LoggerEntity findByToken(@Param("token")String token);

	LoggerEntity findByToken(String token);

	void removeByToken(String token);

}
