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
import com.app.entities.CodingLanguageMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.CodingLanguageRepository;
import com.app.serviceIntf.CodingLanguageServiceIntf;

@RestController
@RequestMapping("/coding-language")
public class CodingLanguageController {

	@Autowired
	private CodingLanguageServiceIntf codingLanguageServiceIntf;

	@Autowired
	private CodingLanguageRepository codingLanguageRepository;

	@PreAuthorize("hasRole('getAllCodingLanguages')")
	@GetMapping()
	public ResponseEntity<?> getAllCodeLang(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<MasterListDto> langs = codingLanguageServiceIntf.findAll(search, pageNo, size);

		if (langs.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success",
					new ListResponseDto(langs.getContent(), langs.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('getCodingLanguageById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getCodeLangById(@PathVariable(value = "id") Long langId) throws ResourceNotFoundException {

		try {

			IMasterDetailDto codeLang = codingLanguageServiceIntf.findById(langId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", codeLang), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "codeLangNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addCodingLanguage')")
	@PostMapping()
	public ResponseEntity<?> addCodeLang(@Valid @RequestBody MasterAddDto newLang, HttpServletRequest request) {
		String name = newLang.getName();
		Optional<CodingLanguageMasterEntity> databaseName = codingLanguageRepository
				.findByNameContainingIgnoreCase(name);

		if (databaseName.isEmpty()) {
			codingLanguageServiceIntf.addCodeLang(newLang,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Language Already Exist", "languageAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editCodingLanguage')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editCodeLang(@PathVariable(value = "id") Long langId,
			@Valid @RequestBody MasterAddDto langBody, HttpServletRequest request) throws ResourceNotFoundException {
		try {
		CodingLanguageMasterEntity langDetail = codingLanguageRepository.findByIdAndIsActiveTrue(langId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		
		String name = langBody.getName();
		Optional<CodingLanguageMasterEntity> databaseName = codingLanguageRepository
				.findByNameContainingIgnoreCase(name);
		
		if (databaseName.isEmpty()) {
			codingLanguageServiceIntf.editCodeLang(langBody, langId,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(new ErrorResponseDto("Language Already Exist", "languageAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
		}
		catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Langauage Already Exist "), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('deleteCodingLanguage')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCodeLang(@PathVariable(value = "id") Long langId, HttpServletRequest request) {

		try {

			codingLanguageServiceIntf.deleteCodeLang(langId,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "codeLangNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('updateCodingLanguageStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long langId, HttpServletRequest request) {

		try {

			codingLanguageServiceIntf.updateCodeLangStatus(langId,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "codeLangNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCodeLang() {

		List<MasterListDto> langs = codingLanguageServiceIntf.findAll();

		if (langs.size() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", langs), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

}
