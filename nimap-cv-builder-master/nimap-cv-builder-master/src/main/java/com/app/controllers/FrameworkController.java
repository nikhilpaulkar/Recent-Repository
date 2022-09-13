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
import com.app.entities.FrameworkMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.FrameworkRepository;
import com.app.serviceIntf.FrameworkServiceIntf;

@RestController
@RequestMapping("/framework")
public class FrameworkController {

	@Autowired
	private FrameworkServiceIntf frameworkServiceIntf;

	@Autowired
	private FrameworkRepository frameworkRepository;
	@PreAuthorize("hasRole('getAllFrameworks')")
	@GetMapping()
	public ResponseEntity<?> getAllFramework(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> frameworks = frameworkServiceIntf.findAll(search, pageNo, size);

		if (frameworks.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(frameworks.getContent(), frameworks.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getFrameworkById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getFrameworkById(@PathVariable(value = "id") Long frameworkId) throws ResourceNotFoundException {

		try {

			IMasterDetailDto framework = frameworkServiceIntf.findById(frameworkId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", framework), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "frameworkNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addFramework')")
	@PostMapping()
	public ResponseEntity<?> addFramework(@Valid @RequestBody MasterAddDto frameworkBody, HttpServletRequest request) {

		String name = frameworkBody.getName();
		Optional<FrameworkMasterEntity> databaseName = frameworkRepository.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
		frameworkServiceIntf.addFramework(frameworkBody, ((UserDataDto) request.getAttribute("userData")).getUserId());
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Framework Already Exist", "frameworkAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editFramework')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editFramework(@PathVariable(name = "id") Long frameworkId, @Valid @RequestBody MasterAddDto frameworkBody, HttpServletRequest request) throws ResourceNotFoundException {
	
		try {
		FrameworkMasterEntity frameworkDetail = frameworkRepository.findByIdAndIsActiveTrue(frameworkId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		String name = frameworkBody.getName();
		Optional<FrameworkMasterEntity> databaseName = frameworkRepository.findByNameContainingIgnoreCase(name);
		if (databaseName.isEmpty()) {
			frameworkServiceIntf.editFramework(frameworkBody, frameworkId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<>(new ErrorResponseDto("Framework Already Exist", "frameworkAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
		}
		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "framework Already Exist "), HttpStatus.NOT_FOUND);

		}
	}

	@PreAuthorize("hasRole('deleteFramework')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteFramework(@PathVariable(name = "id") Long frameworkId, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			frameworkServiceIntf.deleteFramework(frameworkId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "frameworkNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateFrameworkStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long frameworkId, HttpServletRequest request) {

		try {

			frameworkServiceIntf.updateFrameworkStatus(frameworkId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "frameworkNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCodeLang() {

		List<MasterListDto> frameworks = frameworkServiceIntf.findAll();

		if (frameworks.size() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", frameworks), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

}
