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

import com.app.dto.ErrorResponseDto;
import com.app.dto.IMasterDetailDto;
import com.app.dto.ListResponseDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDataDto;
import com.app.entities.DesignationMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.DesignationRepository;
import com.app.serviceIntf.DesignationServiceIntf;

@RestController
@RequestMapping("/designation")
public class DesignationController {

	@Autowired
	private DesignationServiceIntf designationServiceIntf;
	@Autowired
	private DesignationRepository designationRepository;

	@PreAuthorize("hasRole('getAllCodingLanguages')")
	@GetMapping()
	public ResponseEntity<?> getAllDesignation(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> allDesignation = designationServiceIntf.findAll(search, pageNo, size);

		if (allDesignation.getTotalElements() != 0) {

			return new ResponseEntity<>(
					new SuccessResponseDto("Success", "success",
							new ListResponseDto(allDesignation.getContent(), allDesignation.getTotalElements())),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getDesignationById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getDesignationById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

		try {

			IMasterDetailDto designation = designationServiceIntf.findById(id);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", designation), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addDesignation')")
	@PostMapping()
	public ResponseEntity<?> addNewDesignation(@Valid @RequestBody MasterAddDto newDesignation,
			HttpServletRequest request) {
		
		String name = newDesignation.getName();
		Optional<DesignationMasterEntity> databaseName = designationRepository.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
			designationServiceIntf.addDesignation(newDesignation,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Designation Already Exist", "designationAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('editDesignation')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editDesignation(@PathVariable(value = "id") Long id,
			@Valid @RequestBody MasterAddDto newDesignation, HttpServletRequest request)
			throws ResourceNotFoundException {
		try {
		DesignationMasterEntity desigantion = designationRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		
		
			String name = newDesignation.getName();
			Optional<DesignationMasterEntity> databaseName = designationRepository.findByNameContainingIgnoreCase(name);
			
			if (databaseName.isEmpty()) {
			designationServiceIntf.editDesignation(newDesignation, id,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(new ErrorResponseDto("Designation Already Exist", "designationAlreadyExist"),
						HttpStatus.BAD_REQUEST);
			}
		}
		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Designation Already Exist "), HttpStatus.NOT_FOUND);

		}
			
	}

	@PreAuthorize("hasRole('deleteDesignation')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDesignation(@PathVariable(value = "id") Long id, HttpServletRequest request)
			throws ResourceNotFoundException {

		try {

			designationServiceIntf.deleteDesignation(id, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateDesignationStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long designationId, HttpServletRequest request) {

		try {

			designationServiceIntf.updateDesignationStatus(designationId,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "dataNotFound"), HttpStatus.NOT_FOUND);

		}

	}

}
