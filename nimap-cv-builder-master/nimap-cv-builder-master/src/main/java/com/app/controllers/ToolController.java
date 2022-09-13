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
import com.app.entities.ToolsMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.ToolRepository;
import com.app.serviceIntf.ToolsServiceIntf;

@RestController
@RequestMapping("/tool")
public class ToolController {

	@Autowired
	private ToolsServiceIntf toolsServiceIntf;

	@Autowired
	private ToolRepository toolRepository;
	@PreAuthorize("hasRole('getAllTools')")
	@GetMapping()
	public ResponseEntity<?> getAllTools(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> allTools = toolsServiceIntf.findAll(search, pageNo, size);

		if (allTools.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(allTools.getContent(), allTools.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getToolById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getToolById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

		try {

			IMasterDetailDto tool = toolsServiceIntf.findById(id);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", tool), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addTool')")
	@PostMapping()
	public ResponseEntity<?> addNewTool(@Valid @RequestBody MasterAddDto newTool, HttpServletRequest request) {
		String name = newTool.getName();
		Optional<ToolsMasterEntity> databaseName = toolRepository.findByNameContainingIgnoreCase(name);

		if (databaseName.isEmpty()) {
			toolsServiceIntf.addTool(newTool, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Tool Already Exist", "toolAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editTool')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editTool(@PathVariable(value = "id") Long id, @Valid @RequestBody MasterAddDto newTool, HttpServletRequest request) throws ResourceNotFoundException {
		try {
		ToolsMasterEntity toolDetail = toolRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		String name = newTool.getName();
		Optional<ToolsMasterEntity> databaseName = toolRepository.findByNameContainingIgnoreCase(name);
		 
		if (databaseName.isEmpty()) {
			toolsServiceIntf.editTool(newTool, id, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		}
		else
		{
			return new ResponseEntity<>(new ErrorResponseDto("Tool Already Exist", "toolAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
		}
		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Tool Already Exist "), HttpStatus.NOT_FOUND);

		}
	}

	@PreAuthorize("hasRole('deleteTool')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTool(@PathVariable(value = "id") Long id, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			toolsServiceIntf.deleteTool(id, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateToolStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long id, HttpServletRequest request) {

		try {

			toolsServiceIntf.updateToolStatus(id, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "dataNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCodeLang() {

		List<MasterListDto> tools = toolsServiceIntf.findAll();

		if (tools.size() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", tools), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

}
