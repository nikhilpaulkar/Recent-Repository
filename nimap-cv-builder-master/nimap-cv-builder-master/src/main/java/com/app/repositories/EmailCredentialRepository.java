package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.EmailCredentialEntity;

@Repository
public interface EmailCredentialRepository extends JpaRepository<EmailCredentialEntity, Long> {

	EmailCredentialEntity findByEnv(String env);

}
