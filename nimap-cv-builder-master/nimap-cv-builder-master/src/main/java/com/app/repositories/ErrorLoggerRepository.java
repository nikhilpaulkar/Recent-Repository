package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.ErrorLoggerEntity;

public interface ErrorLoggerRepository extends JpaRepository<ErrorLoggerEntity, Long> {
}
