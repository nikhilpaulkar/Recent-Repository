package com.app.serviceIntf;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.app.entities.FileUploadEntity;
import com.app.exceptionsHandling.MyFileNotFoundException;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface FileStorageServiceInterface {

	public FileUploadEntity storeFile(MultipartFile file, String type, HttpServletRequest request);

	public Resource loadFileAsResource(String fileName) throws MyFileNotFoundException;

	public String getFolderName(String type) throws ResourceNotFoundException;

}
