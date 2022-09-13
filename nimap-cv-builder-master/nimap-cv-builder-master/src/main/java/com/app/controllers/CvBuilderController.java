package com.app.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import com.app.dto.CvRequestAddDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.ICvMasterDto;
import com.app.dto.ListResponseDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDataDto;
import com.app.entities.CvRequestEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.MyFileNotFoundException;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.CvRequestRepository;
import com.app.repositories.UserRepository;
import com.app.serviceIntf.CvBuildServiceIntf;
import com.app.serviceIntf.ICvInfoMasterDto;
import com.app.serviceIntf.SecureFileStorageServiceInterface;

@RestController
@RequestMapping("/cv")
public class CvBuilderController {

	@Autowired
	private CvBuildServiceIntf cvBuildServiceIntf;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CvRequestRepository cvRequestRepository;

	@Autowired
	private SecureFileStorageServiceInterface fileStorageServiceInterface;

	@PreAuthorize("hasRole('createOwnCV')")
	@PostMapping()
	public ResponseEntity<?> cvBuilder(@Valid @RequestBody CvRequestAddDto cvBody, HttpServletRequest request)
			throws ResourceNotFoundException {

		UserEntity userData = userRepository.findByIdAndIsActiveTrue(cvBody.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Resouces Not active please make it active"));

		try {

			String path = cvBuildServiceIntf.createCv(cvBody,
					((UserDataDto) request.getAttribute("userData")).getUserId(),
					((UserDataDto) request.getAttribute("userData")).getUserId());
			Resource resource = null;

			// Load file as Resource	
			try {


				resource = fileStorageServiceInterface.loadFileAsResource(path.replace("\\", "/").split("/")[2]);

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

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
					.body(resource);

			// return new ResponseEntity<>(new SuccessResponseDto("Success", "success",
			// null), HttpStatus.CREATED);

		}

		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "userNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('createOtherUserCV')")
	@PostMapping(value = "/{id}")
	public ResponseEntity<?> cvBuilderById(@PathVariable(value = "id") Long userId,

			@Valid @RequestBody CvRequestAddDto cvBody, HttpServletRequest request) throws ResourceNotFoundException {
		try {

			String path = cvBuildServiceIntf.createCv(cvBody, userId,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			Resource resource = null;

			// Load file as Resource
			try {
				resource = fileStorageServiceInterface.loadFileAsResource(path.replace("\\", "/").split("/")[2]);

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

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
					.body(resource);

			// return new ResponseEntity<>(new SuccessResponseDto("Success", "success",
			// null), HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "userNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('getAllCv')")
	@GetMapping()
	public ResponseEntity<?> getAllCv(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<ICvMasterDto> cvs = cvBuildServiceIntf.findAll(search, pageNo, size);

		if (cvs.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success",
					new ListResponseDto(cvs.getContent(), cvs.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getAllCv')")
	@GetMapping("/getCvInfo")
	public ResponseEntity<?> getAllInfoCv(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<ICvInfoMasterDto> cvs = cvBuildServiceIntf.findAllCv(search, pageNo, size);

		if (cvs.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success",
					new ListResponseDto(cvs.getContent(), cvs.getTotalElements())), HttpStatus.OK);
		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getcvById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getcvById(@PathVariable(value = "id") Long cvId) throws ResourceNotFoundException {
		try {
			ICvInfoMasterDto cvDetail = cvBuildServiceIntf.findById(cvId);

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", cvDetail), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Cv Not Found", "cvNotFound"), HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('editCv')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editIde(@PathVariable(value = "id") Long cvId, @Valid @RequestBody CvRequestAddDto cvBody,
			HttpServletRequest request) throws ResourceNotFoundException 
	{
		try  
		{
			cvBuildServiceIntf.editCv(cvBody, cvId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
		} 
		catch (ResourceNotFoundException e) 
		{
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "cvNotFound"),
					HttpStatus.NOT_FOUND);
		}
	}
}
