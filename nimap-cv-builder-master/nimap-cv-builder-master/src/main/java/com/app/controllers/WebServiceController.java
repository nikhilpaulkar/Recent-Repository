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
import com.app.entities.WebServiceMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.WebServicesRepository;
import com.app.serviceIntf.WebServicesServiceIntf;

@RestController
@RequestMapping("/web-service")
public class WebServiceController {

	@Autowired
	private WebServicesServiceIntf webServicesServiceIntf;

	@Autowired
	private WebServicesRepository webServicesRepository;
	@PreAuthorize("hasRole('getAllWebServices')")
	@GetMapping()
	public ResponseEntity<?> getAllIde(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> webServices = webServicesServiceIntf.findAll(search, pageNo, size);

		if (webServices.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(webServices.getContent(), webServices.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getWebServicesById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getIdeById(@PathVariable(value = "id") Long webServiceId) throws ResourceNotFoundException {

		try {

			IMasterDetailDto webServiceDetail = webServicesServiceIntf.findById(webServiceId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", webServiceDetail), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Web Service Not Found", "webServiceNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addWebService')")
	@PostMapping()
	public ResponseEntity<?> addIde(@Valid @RequestBody MasterAddDto webServiceBody, HttpServletRequest request) {
		String name = webServiceBody.getName();
		Optional<WebServiceMasterEntity> databaseName = webServicesRepository.findByNameContainingIgnoreCase(name);

		if (databaseName.isEmpty()) {
			webServicesServiceIntf.addWebServices(webServiceBody,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Web Service Already Exist", "webServiceAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editWebService')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editIde(@PathVariable(value = "id") Long webServiceId, @Valid @RequestBody MasterAddDto webServiceBody, HttpServletRequest request) throws ResourceNotFoundException {
	try {
		WebServiceMasterEntity webServiceDetail = webServicesRepository.findByIdAndIsActiveTrue(webServiceId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		String name = webServiceBody.getName();
		Optional<WebServiceMasterEntity> databaseName = webServicesRepository.findByNameContainingIgnoreCase(name);
		if (databaseName.isEmpty()) {
			webServicesServiceIntf.editWebServices(webServiceBody, webServiceId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(new ErrorResponseDto("Web Service Already Exist", "webServiceAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}
	catch (ResourceNotFoundException e) {

		return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "webService Already Exist "), HttpStatus.NOT_FOUND);

	}

	}

	@PreAuthorize("hasRole('deleteWebService')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIde(@PathVariable(value = "id") Long webServiceId, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			webServicesServiceIntf.deleteWebServices(webServiceId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Web Service Not Found", "webServiceNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateWebServiceStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long id, HttpServletRequest request) {

		try {

			webServicesServiceIntf.updateWebServicesStatus(id, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "webServiceNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCodeLang() {

		List<MasterListDto> webServices = webServicesServiceIntf.findAll();

		if (webServices.size() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", webServices), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

}
