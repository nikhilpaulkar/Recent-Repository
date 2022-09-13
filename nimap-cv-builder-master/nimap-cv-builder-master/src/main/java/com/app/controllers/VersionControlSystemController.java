package com.app.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IMasterDetailDto;
import com.app.dto.ListResponseDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDataDto;
import com.app.entities.VersionControlSystemMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.VersionControlSystemRepository;
import com.app.serviceIntf.VersionControlSystemServiceIntf;

@RestController
@RequestMapping("/version-control")
public class VersionControlSystemController {

	@Autowired
	private VersionControlSystemServiceIntf versionControlSystemServiceIntf;

	@Autowired
	private VersionControlSystemRepository versionControlSystemRepository;
	
	@PreAuthorize("hasRole('getAllVcs')")
	@GetMapping()
	public ResponseEntity<?> getAllIde(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> vcsList = versionControlSystemServiceIntf.findAll(search, pageNo, size);

		if (vcsList.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(vcsList.getContent(), vcsList.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getVcdById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getIdeById(@PathVariable(value = "id") Long vcsId) throws ResourceNotFoundException {

		try {

			IMasterDetailDto vcsDetail = versionControlSystemServiceIntf.findById(vcsId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", vcsDetail), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {
			
			return new ResponseEntity<>(new ErrorResponseDto("Version Control System Not Found", "vcsNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addVcs')")
	@PostMapping()
	public ResponseEntity<?> addIde(@Valid @RequestBody MasterAddDto vcsBody, HttpServletRequest request) {
		String name = vcsBody.getName();
		Optional<VersionControlSystemMasterEntity> databaseName = versionControlSystemRepository
				.findByNameContainingIgnoreCase(name);

		if (databaseName.isEmpty()) {
			versionControlSystemServiceIntf.addVersionControlSystem(vcsBody,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Designation Already Exist", "designationAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editVcs')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editIde(@PathVariable(value = "id") Long vcsId, @Valid @RequestBody MasterAddDto vcsBody, HttpServletRequest request) throws ResourceNotFoundException {
	try {
		VersionControlSystemMasterEntity vcsDetail = versionControlSystemRepository.findByIdAndIsActiveTrue(vcsId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		String name = vcsBody.getName();
		Optional<VersionControlSystemMasterEntity> databaseName = versionControlSystemRepository
				.findByNameContainingIgnoreCase(name);
		if (databaseName.isEmpty()) {

			versionControlSystemServiceIntf.editVersionControlSystem(vcsBody, vcsId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		}
		else {
			return new ResponseEntity<>(new ErrorResponseDto("versionControl Already Exist", "versionControlAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}
	catch (ResourceNotFoundException e) {

		return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "versionControl Already Exist "), HttpStatus.NOT_FOUND);

	}
	}

	@PreAuthorize("hasRole('deleteVcs')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIde(@PathVariable(value = "id") Long vcsId, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			versionControlSystemServiceIntf.deleteVersionControlSystem(vcsId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Version Control System Not Found", "vcsNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateVcsStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long id, HttpServletRequest request) {

		try {

			versionControlSystemServiceIntf.updateVersionControlSystemStatus(id, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "vcsNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCodeLang() {

		List<MasterListDto> vcs = versionControlSystemServiceIntf.findAll();

		if (vcs.size() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", vcs), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

}
