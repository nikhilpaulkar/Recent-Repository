package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.FileUploadEntity;

public interface FileUploadRepository extends JpaRepository<FileUploadEntity, Long> {
}
