package com.app.serviceIntf;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface SecureFileStorageServiceInterface {

	public String storeFile(MultipartFile file);

	public Resource loadFileAsResource(String fileName);

}
