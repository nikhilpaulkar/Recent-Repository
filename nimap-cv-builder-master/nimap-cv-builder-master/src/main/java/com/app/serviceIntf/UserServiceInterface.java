package com.app.serviceIntf;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.app.dto.ChangePasswordDto;
import com.app.dto.EditUserRequestDto;
import com.app.dto.ForgotPasswordDto;
import com.app.dto.IPermissionDto;
import com.app.dto.IUserListDto;
import com.app.dto.UserDataDto;
import com.app.dto.UserDto;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface UserServiceInterface {

	List<IPermissionDto> getUserPermission(Long userId) throws IOException;

	UserEntity findByEmail(String email) throws ResourceNotFoundException;

	void editUser(Long userId, UserDto userBody, Long adminId) throws ResourceNotFoundException;

	void editOnlySameUser(Long userId, UserDto userBody, Long adminId, HttpServletRequest request) throws ResourceNotFoundException;

	Page<IUserListDto> getAllUsers(String search, String from, String to);

	List<UserEntity> getAllUsersCount();

	void updateStatus(Long userId) throws ResourceNotFoundException;

	UserDataDto getUserRole(Long userId) throws ResourceNotFoundException;

	void addUser(UserDto userDetail, Long userId);

	void changePassword(Long userId, ChangePasswordDto userBody, HttpServletRequest request) throws ResourceNotFoundException;

	void forgotPasswordConfirm(String token, ForgotPasswordDto userBody, HttpServletRequest request) throws ResourceNotFoundException;
	
	 
}
