package com.app.controllers;

import java.io.IOException;
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

import com.app.dto.AllStackDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.IProjectExperienceDto;
import com.app.dto.ListResponseDto;
import com.app.dto.ProjectAddDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDataDto;
import com.app.entities.ProjectExperienceEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.ProjectExperienceRepository;
import com.app.serviceIntf.ProjectExperienceServiceIntf;

@RestController
@RequestMapping("/project")
public class ProjectExperienceController {

	@Autowired
	private ProjectExperienceServiceIntf projectExperienceServiceIntf;
	
	@Autowired
	private ProjectExperienceRepository projectExperienceRepository;

	@PreAuthorize("hasRole('getAllProjects')")
	@GetMapping()
	public ResponseEntity<?> getAllProject(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size, HttpServletRequest request) 
	{
		Page<IProjectExperienceDto> projects = projectExperienceServiceIntf.findAll(search, pageNo, size, ((UserDataDto) request.getAttribute("userData")).getUserId());

		if (projects.getTotalElements() != 0) 
		{
			
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(projects.getContent(), projects.getTotalElements())), HttpStatus.OK);
		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);
	}

	@PreAuthorize("hasRole('getAllStacks')")
	@GetMapping("/stacks")
	public ResponseEntity<?> getAllStacks() {

		List<AllStackDto> allStacks = projectExperienceServiceIntf.getAllStack();
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", allStacks), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('getProjectById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getProjectById(@PathVariable(value = "id") Long projectId, HttpServletRequest request) throws ResourceNotFoundException {

		try 
		{

			IProjectExperienceDto project = projectExperienceServiceIntf.findById(projectId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", project), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "projectNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addProject')")
	@PostMapping()
	public ResponseEntity<?> addProject(@Valid @RequestBody ProjectAddDto newProject, HttpServletRequest request)
	{
		String name = newProject.getName();
		Optional<ProjectExperienceEntity> databaseName = projectExperienceRepository.findByNameContainingIgnoreCase(name);
	
		if(databaseName.isEmpty()) 
		{
			projectExperienceServiceIntf.addProject(newProject, ((UserDataDto) request.getAttribute("userData")).getUserId(), ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Project Added Successfully", "projectAddedSuccessfully", newProject), HttpStatus.CREATED);
		}
		else 
		{
			return new ResponseEntity<>(new ErrorResponseDto("Project Already Exist", "projectAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		} 
	}

	@PreAuthorize("hasRole('editProject')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editProject(@PathVariable(value = "id") Long projectId, @Valid @RequestBody ProjectAddDto newProject, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			String name = newProject.getName();
			Optional<ProjectExperienceEntity> databaseName= projectExperienceRepository.findByNameContainingIgnoreCase(name);
			if (databaseName.isEmpty()) {
				projectExperienceServiceIntf.editProject(newProject, projectId, ((UserDataDto) request.getAttribute("userData")).getUserId(), ((UserDataDto) request.getAttribute("userData")).getUserId());
				return new ResponseEntity<>(new SuccessResponseDto("Project Updated Successfully", "projectUpdatedSuccessfully", newProject), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ErrorResponseDto("Project Already Exit","projectAlreadyExit"),HttpStatus.BAD_REQUEST);
			}
			

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "projectNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('deleteProject')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProject(@PathVariable(value = "id") Long projectId, HttpServletRequest request) {

		try {

			projectExperienceServiceIntf.deleteProject(projectId, ((UserDataDto) request.getAttribute("userData")).getUserId(), ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Project Deleted", "projectDeleted", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "codeLangNotFound"), HttpStatus.NOT_FOUND);

		} 

	}

	@GetMapping("/export")
	public ResponseEntity<?> exportProject(HttpServletRequest request) {

		projectExperienceServiceIntf.exportProjects(((UserDataDto) request.getAttribute("userData")).getUserId());
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

	}

	@PostMapping("/bulk-upload")
	public ResponseEntity<?> bulkUploadProject(HttpServletRequest request) throws IOException {

		projectExperienceServiceIntf.projectBulkUpload(27L, ((UserDataDto) request.getAttribute("userData")).getUserId());
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('getProjectsAdmin')")
	@GetMapping("/admin/{userId}")
	public ResponseEntity<?> getAdminAllProject(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size, @PathVariable(value = "userId") Long userId) {

		Page<IProjectExperienceDto> projects = projectExperienceServiceIntf.findAll(search, pageNo, size, userId);

		if (projects.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(projects.getContent(), projects.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getProjectsByIdAdmin')")
	@GetMapping("/admin/{userId}/{id}")
	public ResponseEntity<?> getAdminProjectById(@PathVariable(value = "userId") Long userId, @PathVariable(value = "id") Long projectId) throws ResourceNotFoundException {

		try {

			IProjectExperienceDto project = projectExperienceServiceIntf.findById(projectId, userId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", project), HttpStatus.OK);

		} catch (ResourceNotFoundException e) 
			{

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "projectNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addProjectAdmin')")
	@PostMapping("/admin/{userId}")
	public ResponseEntity<?> addProject(@PathVariable(value = "userId") Long userId, @Valid @RequestBody ProjectAddDto newProject, HttpServletRequest request) {

		projectExperienceServiceIntf.addProject(newProject, userId, ((UserDataDto) request.getAttribute("userData")).getUserId());
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('editProjectAdmin')")
	@PutMapping("/admin/{userId}/{id}")
	public ResponseEntity<?> editProjectByAdmin(@PathVariable(value = "userId") Long userId, @PathVariable(value = "id") Long projectId, @Valid @RequestBody ProjectAddDto newProject, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			projectExperienceServiceIntf.editProject(newProject, projectId, ((UserDataDto) request.getAttribute("userData")).getUserId(), userId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "projectNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('deleteProjectAdmin')")
	@DeleteMapping("/admin/{userId}/{id}")
	public ResponseEntity<?> deleteProjectByAdmin(@PathVariable(value = "userId") Long userId, @PathVariable(value = "id") Long projectId, HttpServletRequest request) {

		try {

			projectExperienceServiceIntf.deleteProject(projectId, ((UserDataDto) request.getAttribute("userData")).getUserId(), userId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "codeLangNotFound"), HttpStatus.NOT_FOUND);

		}

	}

}
