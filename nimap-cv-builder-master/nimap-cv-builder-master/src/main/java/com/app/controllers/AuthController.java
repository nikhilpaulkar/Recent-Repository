package com.app.controllers;

import java.util.Calendar;
// import java.net.http.HttpResponse;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.JwtTokenUtil;
import com.app.dto.AuthRequestDto;
import com.app.dto.AuthResponseDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.ForgotPasswordRequestDto;
import com.app.dto.IPermissionDto;
import com.app.dto.LoggerDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDataDto;
import com.app.dto.UserDto;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.serviceImpl.AuthServiceImpl;
import com.app.serviceIntf.EmailServiceIntf;
import com.app.serviceIntf.ForgotPasswordServiceIntf;
import com.app.serviceIntf.LoggerServiceInterface;
import com.app.serviceIntf.UserServiceInterface;
import com.app.utils.AppSetting;


@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

	public AuthController() {

	}

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthServiceImpl userDetailsService;

	@Autowired
	private UserServiceInterface userServiceInterface;

	@Autowired
	private LoggerServiceInterface loggerServiceInterface;

	@Autowired
	private ForgotPasswordServiceIntf forgotPasswordServiceIntf;

	@Autowired
	private EmailServiceIntf emailServiceIntf;

	@Autowired
	private AppSetting appSetting;

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthRequestDto authenticationRequest) throws Exception, ResourceNotFoundException {

		try {

			UserEntity userEntity = userServiceInterface.findByEmail(authenticationRequest.getEmail());

			if (!userDetailsService.comparePassword(authenticationRequest.getPassword(), userEntity.getPassword())) {

				return new ResponseEntity<>(new ErrorResponseDto("Invalid Credential", "invalidCreds"), HttpStatus.UNAUTHORIZED);
			}
			
			System.out.println("DATa"+userEntity.getEmail());
			final String token = jwtTokenUtil.generateToken(userEntity);
			 
			List<IPermissionDto> permissions = userServiceInterface.getUserPermission(userEntity.getId());
			LoggerDto logger = new LoggerDto();
			logger.setToken(token);
			Calendar calender = Calendar.getInstance();
			calender.add(Calendar.HOUR_OF_DAY, 5);
			logger.setExpireAt(calender.getTime());
			loggerServiceInterface.createLogger(logger, userEntity);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "success", new AuthResponseDto(token, permissions,userEntity.getEmail(),userEntity.getName(),userEntity.getId())), HttpStatus.OK);
			
		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "userNotFound"), HttpStatus.NOT_FOUND);

		}

	}
	// @PreAuthorize("hasRole('addUser')")
	// @RequestMapping(value = "/register", method = RequestMethod.POST)
	// public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto user) throws Exception, DataIntegrityViolationException {
	// try {
	// userServiceInterface.addUser(user);
	// return new ResponseEntity<>(new SuccessResponseDto("User Created", "userCreated", null), HttpStatus.CREATED);
	// } catch (DataIntegrityViolationException e) {
	// return new ResponseEntity<>(new ErrorResponseDto("User Already Exist", "userAlreadyExist"), HttpStatus.BAD_REQUEST);
	// }
	//
	// }

	@GetMapping("/logout")
	public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token, HttpServletRequest request) throws Exception {

		loggerServiceInterface.logoutUser(token, ((UserDataDto) request.getAttribute("userData")).getUserId(), ((UserDataDto) request.getAttribute("userData")).getEmail());
		return new ResponseEntity<>(new ErrorResponseDto("Logout Successfully", "logoutSuccess"), HttpStatus.OK);

	}

	@PostMapping("/forgot-pass")
	public ResponseEntity<?> forgotPass(@Valid @RequestBody ForgotPasswordRequestDto forgotPassBody, HttpServletRequest request) throws Exception {

		try {

			UserEntity userEntity = userServiceInterface.findByEmail(forgotPassBody.getEmail());
			final String token = jwtTokenUtil.generateTokenOnForgotPass(userEntity.getEmail());
			final String baseUrl = appSetting.getAllAppSetting().getSettings().get("frontendbaseurl");
			final String passUrl = appSetting.getAllAppSetting().getSettings().get("forgotpasswordurl");
			final String url = "To confirm your account, please click here : " + baseUrl + passUrl + "?token=" + token;
			Calendar calender = Calendar.getInstance();
			calender.add(Calendar.MINUTE, 5);
			forgotPasswordServiceIntf.createForgotPasswordRequest(userEntity.getId(), token, calender.getTime());
			emailServiceIntf.sendSimpleMessage(userEntity.getEmail(), "Test", url);
			return new ResponseEntity<>(new SuccessResponseDto("Password reset link sent on Registerd Email", "passwordRestLinkMail", null), HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "userNotFound"), HttpStatus.NOT_FOUND);

		}
		
		
		
		



	}

}
