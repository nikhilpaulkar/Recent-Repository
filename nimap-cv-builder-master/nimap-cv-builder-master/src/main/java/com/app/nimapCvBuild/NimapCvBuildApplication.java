package com.app.nimapCvBuild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.app.properties.FileStorageProperties;
import com.app.properties.SecureFileStorageProperties;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class, scanBasePackages = { "com.app.config" })
@ComponentScan({ "com.app.*" })
@EntityScan("com.app.*")
@EnableJpaRepositories("com.app.repositories")
@EnableCaching
@EnableConfigurationProperties({ FileStorageProperties.class, SecureFileStorageProperties.class })
public class NimapCvBuildApplication {

	public static void main(String[] args) {

		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(NimapCvBuildApplication.class, args);

	}

}
