package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.ApiLoggerEntity;

public interface ApiLoggerRepository extends JpaRepository<ApiLoggerEntity, Long> {
}
