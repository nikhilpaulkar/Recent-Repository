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
import com.app.entities.OperatingSystemMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.OperatingSystemRepository;
import com.app.serviceIntf.OperatingSystemServiceIntf;

@RestController
@RequestMapping("/os")
public class OperatingSystemController {

	@Autowired
	private OperatingSystemServiceIntf operatingSystemServiceIntf;

	@Autowired
	private OperatingSystemRepository operatingSystemRepository;
	
	@PreAuthorize("hasRole('getAllOs')")
	@GetMapping()
	public ResponseEntity<?> getAllIde(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> osList = operatingSystemServiceIntf.findAll(search, pageNo, size);

		if (osList.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(osList.getContent(), osList.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getOsById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getIdeById(@PathVariable(value = "id") Long osId) throws ResourceNotFoundException {

		try {

			IMasterDetailDto osDetail = operatingSystemServiceIntf.findById(osId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", osDetail), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Operating System Not Found", "osNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addOs')")
	@PostMapping()
	public ResponseEntity<?> addIde(@Valid @RequestBody MasterAddDto osBody, HttpServletRequest request) {
		String name = osBody.getName();
		Optional<OperatingSystemMasterEntity> databaseName = operatingSystemRepository.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
		operatingSystemServiceIntf.addOs(osBody, ((UserDataDto) request.getAttribute("userData")).getUserId());
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Operating System Already Exist", "operatingSystemAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editOs')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editIde(@PathVariable(value = "id") Long osId, @Valid @RequestBody MasterAddDto osBody, HttpServletRequest request) throws ResourceNotFoundException {
		try {
		OperatingSystemMasterEntity osDetail = operatingSystemRepository.findByIdAndIsActiveTrue(osId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		String name = osBody.getName();
		Optional<OperatingSystemMasterEntity> databaseName = operatingSystemRepository.findByNameContainingIgnoreCase(name);
		if (databaseName.isEmpty()) {
		operatingSystemServiceIntf.editOs(osBody, osId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		}  
	 
	else
	{
		return new ResponseEntity<>(new ErrorResponseDto("Operating System Already Exist", "operatingSystemAlreadyExist"),
				HttpStatus.BAD_REQUEST);	

	}
		}
		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Operating System Already Exist "), HttpStatus.NOT_FOUND);

		}
	}

	@PreAuthorize("hasRole('deleteOs')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIde(@PathVariable(value = "id") Long osId, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			operatingSystemServiceIntf.deleteOs(osId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Operating System Not Found", "osNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('udpateOsStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long osId, HttpServletRequest request) {

		try {

			operatingSystemServiceIntf.updateOsStatus(osId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "osNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCodeLang() {

		List<MasterListDto> os = operatingSystemServiceIntf.findAll();

		if (os.size() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", os), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

}
