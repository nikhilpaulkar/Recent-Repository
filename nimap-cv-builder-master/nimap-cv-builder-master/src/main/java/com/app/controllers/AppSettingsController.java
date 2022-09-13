package com.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AddSettingDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.SuccessResponseDto;
import com.app.entities.AppSettingsEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.serviceIntf.AppSettingServiceInterface;

@RestController
@RequestMapping("/app-setting")
public class AppSettingsController {

	@Autowired
	private AppSettingServiceInterface appSettingServiceInterface;

	@PreAuthorize("hasRole('getAllSettings')")
	@GetMapping()
	public ResponseEntity<?> getAllSettings() {

		List<AppSettingsEntity> settings = appSettingServiceInterface.getAllSetting();
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", settings), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('addSetting')")
	@PostMapping()
	public ResponseEntity<?> addSetting(@Valid @RequestBody AddSettingDto settingBody) {

		appSettingServiceInterface.addSetting(settingBody);
		return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('editSetting')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editSetting(@PathVariable(value = "id") Long settingId, @Valid @RequestBody AddSettingDto settingDto) throws ResourceNotFoundException {

		try {

			appSettingServiceInterface.updateSettingById(settingId, settingDto);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "settingNotFound"), HttpStatus.BAD_REQUEST);

		}

	}

}
