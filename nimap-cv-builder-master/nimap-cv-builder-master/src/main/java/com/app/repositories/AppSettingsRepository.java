package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.AppSettingsEntity;

public interface AppSettingsRepository extends JpaRepository<AppSettingsEntity, Long> {
}
