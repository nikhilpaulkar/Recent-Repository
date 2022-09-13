package com.app.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ErrorResponseDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UploadFileResponse;
import com.app.entities.FileUploadEntity;
import com.app.exceptionsHandling.MyFileNotFoundException;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.serviceIntf.FileStorageServiceInterface;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileStorageServiceInterface fileStorageServiceInterface;

	@PostMapping("/upload-file")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "") String type, HttpServletRequest request) {

		FileUploadEntity fileDetail = new FileUploadEntity();

		try {

			fileDetail = fileStorageServiceInterface.storeFile(file, fileStorageServiceInterface.getFolderName(type), request);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "invalidUploadType"), HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(new SuccessResponseDto("File Uploaded Successfully", "fileUploadSuccessfully", new UploadFileResponse(fileDetail.getId(), fileDetail.getFilename(), type)), HttpStatus.CREATED);

	}
	// @PostMapping("/uploadMultipleFiles")
	// public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
	// return Arrays.asList(files)
	// .stream()
	// .map(file -> uploadFile(file))
	// .collect(Collectors.toList());
	// }

	@GetMapping("/downloadFile/{path}/{fileName:.+}")
	public ResponseEntity<?> downloadFile(@PathVariable String path, @PathVariable String fileName, HttpServletRequest request) {

		Resource resource = null;

		// Load file as Resource
		try {

			resource = fileStorageServiceInterface.loadFileAsResource(path + "/" + fileName);

		} catch (MyFileNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "fileNotFound"), HttpStatus.NOT_FOUND);

		}

		// Try to determine file's content type
		String contentType = null;

		try {

			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

		} catch (IOException ex) {

			System.out.print("Could not determine file type.");

		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {

			contentType = "application/octet-stream";

		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"").body(resource);

	}

}
