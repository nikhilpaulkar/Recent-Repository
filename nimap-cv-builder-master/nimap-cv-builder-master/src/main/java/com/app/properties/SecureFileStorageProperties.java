package com.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "secure-file1")
public class SecureFileStorageProperties {

	private String uploadDir;

	public String getUploadDir() {

		return uploadDir;

	}

	public void setUploadDir(String uploadDir) {

		this.uploadDir = uploadDir;

	}

}
