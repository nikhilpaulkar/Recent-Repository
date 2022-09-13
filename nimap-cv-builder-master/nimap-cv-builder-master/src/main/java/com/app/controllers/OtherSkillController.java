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
import com.app.entities.OtherSkillMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.OtherSkillRepository;
import com.app.serviceIntf.OtherSkillServiceIntf;

@RestController
@RequestMapping("/skill")
public class OtherSkillController {

	@Autowired
	private OtherSkillServiceIntf otherSkillServiceIntf;

	@Autowired
	private OtherSkillRepository otherSkillRepository;
	@PreAuthorize("hasRole('getAllSkills')")
	@GetMapping()
	public ResponseEntity<?> getAllIde(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> otherSkills = otherSkillServiceIntf.findAll(search, pageNo, size);

		if (otherSkills.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new ListResponseDto(otherSkills.getContent(), otherSkills.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getSkillById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getIdeById(@PathVariable(value = "id") Long skillId) throws ResourceNotFoundException {

		try {

			IMasterDetailDto skillDetail = otherSkillServiceIntf.findById(skillId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", skillDetail), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Skill Not Found", "skillNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addSkill')")
	@PostMapping()
	public ResponseEntity<?> addIde(@Valid @RequestBody MasterAddDto skillBody, HttpServletRequest request) {
		String name = skillBody.getName();
		Optional<OtherSkillMasterEntity> databaseName = otherSkillRepository.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
		otherSkillServiceIntf.addOtherSkill(skillBody, ((UserDataDto) request.getAttribute("userData")).getUserId());
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Skill Already Exist", "skillAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editSkill')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editIde(@PathVariable(value = "id") Long skillId, @Valid @RequestBody MasterAddDto skillBody, HttpServletRequest request) throws ResourceNotFoundException {
		try {
		OtherSkillMasterEntity otherSkillDetail = otherSkillRepository.findByIdAndIsActiveTrue(skillId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		String name = skillBody.getName();
		Optional<OtherSkillMasterEntity> databaseName = otherSkillRepository.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
			otherSkillServiceIntf.editOtherSkill(skillBody, skillId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Skill Already Exist", "skillAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
		}
		
		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Skill Already Exist "), HttpStatus.NOT_FOUND);

		}
	}

	@PreAuthorize("hasRole('deleteSkill')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIde(@PathVariable(value = "id") Long skillId, HttpServletRequest request) throws ResourceNotFoundException {

		try {

			otherSkillServiceIntf.deleteOtherSkill(skillId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Skill Not Found", "skillNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateSkillStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long skillId, HttpServletRequest request) {

		try {

			otherSkillServiceIntf.updateOtherSkillStatus(skillId, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "skillNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCodeLang() {

		List<MasterListDto> os = otherSkillServiceIntf.findAll();

		if (os.size() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", os), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

}
