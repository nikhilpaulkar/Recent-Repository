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
import com.app.dto.ListResponseDto;
import com.app.dto.MasterListVendor;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDataDto;
import com.app.dto.VendorMasterDto;
import com.app.entities.VendorMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.VendorRepository;
import com.app.serviceIntf.VendorServiceIntrf;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	private VendorServiceIntrf vendorServiceIntrf;

	@Autowired
	private VendorRepository vendorRepository;

	@PreAuthorize("hasRole('getAllVendors')")
	@GetMapping()
	public ResponseEntity<?> getAllVendor(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<VendorMasterDto> vendors = vendorServiceIntrf.findAll(search, pageNo, size);

		if (vendors.getTotalElements() != 0) {
			
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success",
					new ListResponseDto(vendors.getContent(), vendors.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getvendorById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getVendorById(@PathVariable(value = "id") Long frameworkId)
			throws ResourceNotFoundException {

		try {

			VendorMasterDto vendors = vendorServiceIntrf.findById(frameworkId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", vendors), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "vendorsNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addVendor')")
	@PostMapping()
	public ResponseEntity<?> addVendor(@Valid @RequestBody MasterListVendor vendorBody, HttpServletRequest request) {

		String name = vendorBody.getName();
		Optional<VendorMasterEntity> databaseName = vendorRepository.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
			vendorServiceIntrf.addVendor(vendorBody, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Vendor Already Exist", "vendorAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editVendor')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editVendor(@PathVariable(name = "id") Long vendorkId,
			@Valid @RequestBody MasterListVendor vendorBody, HttpServletRequest request)
			throws ResourceNotFoundException {

		try {
			VendorMasterEntity vendorDetail = vendorRepository.findByIdAndIsActiveTrue(vendorkId).orElseThrow(
					() -> new ResourceNotFoundException("first make it the active switch then do the editing"));

			String name = vendorBody.getName();
			
		 
			
			if(name.equals(vendorDetail.getName()) )
			{
			 
				vendorServiceIntrf.editVendor(vendorBody, vendorkId,
						((UserDataDto) request.getAttribute("userData")).getUserId());
				return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
				
			}
			
			
			Optional<VendorMasterEntity> databaseName = vendorRepository.findByNameContainingIgnoreCase(name);
			if (databaseName.isEmpty()) {
				vendorServiceIntrf.editVendor(vendorBody, vendorkId,
						((UserDataDto) request.getAttribute("userData")).getUserId());
				return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ErrorResponseDto("Vendor Name Already Exist", "VendorAlreadyExist"),
						HttpStatus.BAD_REQUEST);
			}
		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Data Not Found"),
					HttpStatus.NOT_FOUND);

		}
	}

	@PreAuthorize("hasRole('deleteVendor')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVendor(@PathVariable(name = "id") Long vendorId, HttpServletRequest request)
			throws ResourceNotFoundException {

		try {

			vendorServiceIntrf.deleteVendor(vendorId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "vendorNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateVendorStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long vendorkId, HttpServletRequest request) {

		try {

			vendorServiceIntrf.updateVendorStatus(vendorkId,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "vendorNotFound"), HttpStatus.NOT_FOUND);

		}

	}

//	@GetMapping("/all")
//	public ResponseEntity<?> getAllCodeLang() {
//
//		List<VendorMasterDto> vendors = vendorServiceIntrf.findAll();
//
//		if (vendors.size() != 0) {
//
//			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", vendors), HttpStatus.OK);
//
//		}
//
//		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);
//
//	}

}
