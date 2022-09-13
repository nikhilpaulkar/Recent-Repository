package com.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ErrorResponseDto;
import com.app.dto.PermissionRequestDto;
import com.app.dto.SuccessResponseDto;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.serviceIntf.PermissionServiceInterface;

@RestController
@RequestMapping("/permission")
@Validated
public class PermissionController {

	public PermissionController() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private PermissionServiceInterface permissionServiceInterface;

	@PreAuthorize("hasRole('addPermission')")
	@PostMapping()
	public ResponseEntity<?> addPermission(@Valid @RequestBody PermissionRequestDto permissionBody) {

		permissionServiceInterface.addPermission(permissionBody);
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('editPermission')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editPermission(@PathVariable(value = "id") Long permissionId, @Valid @RequestBody PermissionRequestDto permissionBody) throws ResourceNotFoundException {

		try {

			permissionServiceInterface.editPermission(permissionBody, permissionId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "permissionNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('deletePermission')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> editEntity(@PathVariable(value = "id") Long permissionId) throws ResourceNotFoundException {

		try {

			permissionServiceInterface.deletePermission(permissionId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "permissionNotFound"), HttpStatus.NOT_FOUND);

		}

	}

}
