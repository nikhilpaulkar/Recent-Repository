package com.app.controllers;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.app.dto.AddPermissionRequestDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.IResponsibilityDetailDto;
import com.app.dto.ListResponseDto;
import com.app.dto.ResponsibilityAddDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDataDto;
import com.app.entities.ResponsibilityMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.ResponsibilityRepository;
import com.app.serviceIntf.ResponsibilityServiceIntf;

@RestController
@RequestMapping("/responsibility")
public class ResponsibilityController {

	@Autowired
	private ResponsibilityServiceIntf responsibilityServiceIntf;

	@Autowired
	private ResponsibilityRepository responsibilityRepository;
	@PreAuthorize("hasRole('getAllResponsibilities')")
	@GetMapping
	public ResponseEntity<?> getAllResponsibilities(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<IResponsibilityDetailDto> responsibilities = responsibilityServiceIntf.getResponsibilities(search, pageNo,
				size);

		if (responsibilities.getTotalElements() != 0) {

			return new ResponseEntity<>(
					new SuccessResponseDto("Success", "success",
							new ListResponseDto(responsibilities.getContent(), responsibilities.getTotalElements())),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getAllResponsibilityById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getResponsibilityById(@PathVariable(value = "id") Long id) {

		try {

			IResponsibilityDetailDto responsibility = responsibilityServiceIntf.getResponsibilityById(id);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", responsibility), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "responsibilityNotFound"),
					HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addResponsibility')")
	@PostMapping
	public ResponseEntity<?> addResponsibility(@Valid @RequestBody ResponsibilityAddDto reqBody,
			HttpServletRequest request) throws Exception, DataIntegrityViolationException {
		
		String name = reqBody.getName();
		Optional<ResponsibilityMasterEntity> databaseName = responsibilityRepository.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
		try {
			responsibilityServiceIntf.addResponsibility(reqBody,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(
					new SuccessResponseDto("Responsibility Created", "Responsibility Created", reqBody),
					HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(
					new ErrorResponseDto("Langauage is not available", "Langauage is not available"),
					HttpStatus.BAD_REQUEST);
		}
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Responsibility Already Exist", "responsibilityAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editResponsibility')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editResponsibilityById(@PathVariable(value = "id") Long id,
			@Valid @RequestBody ResponsibilityAddDto reqBody, HttpServletRequest request) throws ResourceNotFoundException {
		try {
		ResponsibilityMasterEntity responsibility = responsibilityRepository.findByIdAndIsActiveTrue(id)
				.orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));		
		String name = reqBody.getName();
		Optional<ResponsibilityMasterEntity> databaseName = responsibilityRepository.findByNameContainingIgnoreCase(name);
		if (databaseName.isEmpty()) {
		try {

			responsibilityServiceIntf.editResponsibility(reqBody, id,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "responsibilityNotFound"),
					HttpStatus.NOT_FOUND);

		}
		}
		else
		{
			return new ResponseEntity<>(new ErrorResponseDto("Responsibility Already Exist", "responsibilityAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
		}
		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "responsibility Already Exist "), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateResponsibilityStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateResponsibilityStatus(@PathVariable(value = "id") Long id,
			HttpServletRequest request) {

		try {

			responsibilityServiceIntf.updateResponsibilityStatusById(id,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "responsibilityNotFound"),
					HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('deleteResponsibility')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteResposibility(@PathVariable(value = "id") Long id, HttpServletRequest request) {

		try {

			responsibilityServiceIntf.deleteResponsibility(id,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "responsibilityNotFound"),
					HttpStatus.NOT_FOUND);

		}

	}

	@PostMapping("/langs")
	public ResponseEntity<?> getResponsibilityByCodeLang(@Valid @RequestBody AddPermissionRequestDto reqBody) {

		ArrayList<IResponsibilityDetailDto> responsibilities = responsibilityServiceIntf
				.getResponsibilityByCodeLangId(reqBody.getPermissions());
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", responsibilities), HttpStatus.OK);

	}

}
