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
import com.app.entities.IDEMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.IdeRepository;
import com.app.serviceIntf.IdeServiceIntf;

@RestController
@RequestMapping("/ide")
public class IdeController {

	@Autowired
	private IdeServiceIntf ideServiceIntf;

	@Autowired
	private IdeRepository ideRepository;
	@PreAuthorize("hasRole('getAllIde')")
	@GetMapping()
	public ResponseEntity<?> getAllIde(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> ides = ideServiceIntf.findAll(search, pageNo, size);

		if (ides.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(ides.getContent(), ides.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getIdeById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getIdeById(@PathVariable(value = "id") Long ideId) throws ResourceNotFoundException {

		try {

			IMasterDetailDto ideDetail = ideServiceIntf.findById(ideId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", ideDetail), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("IDE Not Found", "ideNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addIde')")
	@PostMapping()
	public ResponseEntity<?> addIde(@Valid @RequestBody MasterAddDto ideBody, HttpServletRequest request) {
		String name = ideBody.getName();
		Optional<IDEMasterEntity> databaseName = ideRepository.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
		ideServiceIntf.addIde(ideBody, ((UserDataDto) request.getAttribute("userData")).getUserId());
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Software Already Exist", "softwareAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editIde')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editIde(@PathVariable(value = "id") Long ideId, @Valid @RequestBody MasterAddDto ideBody, HttpServletRequest request) throws ResourceNotFoundException {
		try {
		IDEMasterEntity ideDetail = ideRepository.findByIdAndIsActiveTrue(ideId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		
		String name = ideBody.getName();
			Optional<IDEMasterEntity> databaseName = ideRepository.findByNameContainingIgnoreCase(name);
			if (databaseName.isEmpty()) {
			ideServiceIntf.editIde(ideBody, ideId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ErrorResponseDto("Software Already Exist", "softwareAlreadyExist"),
						HttpStatus.BAD_REQUEST);
			}
		}
		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Software Already Exist "), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('deleteIde')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIde(@PathVariable(value = "id") Long ideId, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			ideServiceIntf.deleteIde(ideId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("IDE Not Found", "ideNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateIdeStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long ideId, HttpServletRequest request) {

		try {

			ideServiceIntf.updateIdeStatus(ideId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "ideNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCodeLang() {

		List<MasterListDto> ides = ideServiceIntf.findAll();

		if (ides.size() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", ides), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

}
