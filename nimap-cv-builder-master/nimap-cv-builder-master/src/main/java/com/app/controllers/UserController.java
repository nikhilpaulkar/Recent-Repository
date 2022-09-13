package com.app.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ChangePasswordDto;
import com.app.dto.DashboardDto;
import com.app.dto.EditUserRequestDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.ForgotPasswordDto;
import com.app.dto.IUserListDto;
import com.app.dto.ListResponseDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDataDto;
import com.app.dto.UserDto;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.UserRepository;
import com.app.serviceIntf.CvBuildServiceIntf;
import com.app.serviceIntf.UserServiceInterface;

@RestController
@RequestMapping("/user")
public class UserController {

	public UserController() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private UserServiceInterface userServiceInterface;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CvBuildServiceIntf cvBuildServiceIntf;

	@PreAuthorize("hasRole('getAllUser')")
	@GetMapping()
	public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String size) {

		Page<IUserListDto> users = userServiceInterface.getAllUsers(search, pageNo, size);

		if (users.getTotalElements() != 0) {

			return new ResponseEntity<>(new SuccessResponseDto("Success", "success",
					new ListResponseDto(users.getContent(), users.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	// @PreAuthorize("hasRole('getAllUser')")
	@GetMapping("/dashboard/count")
	public ResponseEntity<?> getAllUsersCount() {

		// Page<IUserListDto> users = userServiceInterface.getAllUsers(search, pageNo,
		// size);
		Long userCount = (long) userServiceInterface.getAllUsersCount().size();
		Long cvCount = (long) cvBuildServiceIntf.getAllEmployee().size();
  
		if (Long.SIZE != 0) {

			return new ResponseEntity<>(
					new SuccessResponseDto("Success", "success", new DashboardDto(userCount, cvCount)), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

	}

	@PreAuthorize("hasRole('editUser')")
	@PutMapping("/{id}")
	public ResponseEntity<?> editUserAndRole(@PathVariable(value = "id") Long userId,
			@Valid @RequestBody EditUserRequestDto userBody, HttpServletRequest request)
			throws ResourceNotFoundException {

		try {
			UserEntity userData = userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
			
			userServiceInterface.editUser(userId, userBody,
					((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "userNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('editUser')")
	@PutMapping("/editnoruser/{id}")
	public ResponseEntity<?> userEdits(@PathVariable(value = "id") Long userId, @Valid @RequestBody UserDto userBody,
			HttpServletRequest request) throws ResourceNotFoundException {

		try {
			UserEntity userData = userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
			
			userServiceInterface.editOnlySameUser(userId, userBody,
					((UserDataDto) request.getAttribute("userData")).getUserId(), request);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Access Denied"), HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('changeUserStatus')")
	@PutMapping("/status/{id}")
	public ResponseEntity<?> editUserStatus(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {

		try {

			userServiceInterface.updateStatus(userId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "userNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('getUserById')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {

		try {

			UserDataDto userDetail = userServiceInterface.getUserRole(userId);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", userDetail), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "userNotFound"), HttpStatus.NOT_FOUND);

		}

	}

	@PreAuthorize("hasRole('addUser')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto user, HttpServletRequest request)
			throws Exception, DataIntegrityViolationException {
		String email = user.getEmail();
		Optional<UserEntity> databaseName = userRepository.findByEmailContainingIgnoreCase(email);

		if (databaseName.isEmpty()) {
			userServiceInterface.addUser(user, ((UserDataDto) request.getAttribute("userData")).getUserId());
			return new ResponseEntity<>(new SuccessResponseDto("User Created", "userCreated", null),
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("User Email Id Already Exist", "userEmailIdAlreadyExist"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('editUser')")
	@PutMapping("/changePass/{id}")
	public ResponseEntity<?> changePasswords(@PathVariable(value = "id") Long userId,
			@Valid @RequestBody ChangePasswordDto userBody, HttpServletRequest request)
			throws ResourceNotFoundException {

		try {

			userServiceInterface.changePassword(userId, userBody, request);
			return new ResponseEntity<>(new SuccessResponseDto("password Updated", "password Updated succefully", null),
					HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Access Denied"), HttpStatus.BAD_GATEWAY);

		}

	}

	

	@PutMapping("/forgot-pass-confirm")
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDto userBody, HttpServletRequest request)
			throws ResourceNotFoundException {

		try {

			userServiceInterface.forgotPasswordConfirm(userBody.getToken(), userBody, request);
			return new ResponseEntity<>(new SuccessResponseDto("password Updated", "password Updated succefully", null),
					HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Access Denied"), HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('getAllUser')")
	@GetMapping("/getByJoinDate")
	public @ResponseBody ResponseEntity<?> getByDates(
			@RequestParam(defaultValue = "2021-01-01", value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to)
			throws ResourceNotFoundException{

		try {

//			List<IUserListDto> users = userServiceInterface.findbyJoiningDate(from, to);
			List<IUserListDto> users = userRepository.findByDateOfJoiningBetween(from, to);
			System.out.println(users.size());

			return new ResponseEntity<>(new SuccessResponseDto("succefully", "succefully", users),
					HttpStatus.OK);
		}

		catch (Exception e) 
		{
			return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "dataNotFound"), HttpStatus.NOT_FOUND);

		}

	}
	
}
