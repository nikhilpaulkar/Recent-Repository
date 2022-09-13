package com.app.controllers;

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

import com.app.dto.AddPermissionRequestDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.IRoleDetailDto;
import com.app.dto.IRoleListDto;
import com.app.dto.ListResponseDto;
import com.app.dto.RoleDto;
import com.app.dto.RolePermissionDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDataDto;
import com.app.entities.RoleEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.RoleRepository;
import com.app.serviceIntf.RoleServiceInterface;

@RestController
@RequestMapping("/role")
public class RoleController {

	public RoleController() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private RoleServiceInterface roleServiceInterface;

	@Autowired
	private RoleRepository roleRepository;
	@PreAuthorize("hasRole('getAllRole')")
	@GetMapping()
	public ResponseEntity<?> getAllRoles(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<IRoleListDto> roles = roleServiceInterface.getAllRoles(search, pageNo, size);

		if (roles.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(roles.getContent(), roles.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('addRole')")
	@PostMapping()
	public ResponseEntity<?> addRole(@Valid @RequestBody RoleDto roleDto, HttpServletRequest request) {
		String name = roleDto.getRoleName();
		Optional<RoleEntity> databaseName = roleRepository.findByRoleNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
			roleServiceInterface.addRole(roleDto, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Role Already Exist", "roleAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editRole')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editRole(@PathVariable(value = "id") Long roleId, @Valid @RequestBody RoleDto roleDto, HttpServletRequest request) {

		try {

			roleServiceInterface.updateRole(roleDto, roleId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "roleNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('deleteRole')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable(value = "id") Long roleId, HttpServletRequest request) {

		try {

			roleServiceInterface.deleteRole(roleId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "roleNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('GetRoleById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getRoleById(@PathVariable(value = "id") Long roleId) {

		try {

			IRoleDetailDto roleEntity = roleServiceInterface.getRoleById(roleId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", roleEntity), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "roleNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('GetRoleAndPermissionById')")
	@GetMapping("/permission/{id}")
	public ResponseEntity<?> getRoleAndPermissionById(@PathVariable(value = "id") Long roleId) {

		try {

			RolePermissionDto rolePermissionData = roleServiceInterface.getRoleAndPermissionById(roleId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", rolePermissionData), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "roleNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addPermissionToRole')")
	@PostMapping("/permission/{id}")
	public ResponseEntity<?> AddPermissionToRole(@PathVariable(value = "id") Long roleId, @Valid @RequestBody AddPermissionRequestDto permissions) {

		try {

			roleServiceInterface.addPermissionsToRole(roleId, permissions.getPermissions());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "roleNotFound"), HttpStatus.NOT_FOUND);

		}

	}

}
