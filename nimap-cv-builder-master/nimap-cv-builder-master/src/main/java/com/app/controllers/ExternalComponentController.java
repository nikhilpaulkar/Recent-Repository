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
import com.app.entities.ExternalComponentMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.ExternalComponentRepository;
import com.app.serviceIntf.ExternalComponentServiceIntf;

@RestController
@RequestMapping("/external-component")
public class ExternalComponentController {

	@Autowired
	private ExternalComponentServiceIntf externalComponentServiceIntf;

	@Autowired
	private ExternalComponentRepository externalComponentRepository;
	@PreAuthorize("hasRole('getAllExtComp')")
	@GetMapping()
	public ResponseEntity<?> getAllExtComp(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> extComponents = externalComponentServiceIntf.findAll(search, pageNo, size);

		if (extComponents.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(extComponents.getContent(), extComponents.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getExtCompById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getIdeById(@PathVariable(value = "id") Long extCompId) throws ResourceNotFoundException {

		try {

			IMasterDetailDto extCompDetail = externalComponentServiceIntf.findById(extCompId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", extCompDetail), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("External Component Not Found", "extCompNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addExpComp')")
	@PostMapping()
	public ResponseEntity<?> addIde(@Valid @RequestBody MasterAddDto extCompBody, HttpServletRequest request) {

		String name = extCompBody.getName();
		Optional<ExternalComponentMasterEntity> databaseName = externalComponentRepository.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
		externalComponentServiceIntf.addExtComponent(extCompBody, ((UserDataDto) request.getAttribute("userData")).getUserId());
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("External Component Already Exist", "externalComponentAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editExpComp')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editIde(@PathVariable(value = "id") Long extCompId, @Valid @RequestBody MasterAddDto extCompBody, HttpServletRequest request) throws ResourceNotFoundException {
		try {
		ExternalComponentMasterEntity extComp = externalComponentRepository.findByIdAndIsActiveTrue(extCompId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		String name = extCompBody.getName();
		Optional<ExternalComponentMasterEntity> databaseName = externalComponentRepository.findByNameContainingIgnoreCase(name);
		if (databaseName.isEmpty()) {
			externalComponentServiceIntf.editExtComponent(extCompBody, extCompId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(new ErrorResponseDto("External Component Already Exist", "externalComponentAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
		}
		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "External Component Already Exist "), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('deleteExpComp')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIde(@PathVariable(value = "id") Long extCompId, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			externalComponentServiceIntf.deleteExtComponent(extCompId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("External Component Not Found", "extCompNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateExpCompStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long extCompId, HttpServletRequest request) {

		try {

			externalComponentServiceIntf.updateExtComponentStatus(extCompId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "extCompNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCodeLang() {

		List<MasterListDto> extComps = externalComponentServiceIntf.findAll();

		if (extComps.size() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", extComps), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

}
