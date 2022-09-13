package com.app.serviceIntf;

import com.app.dto.LoggerDto;
import com.app.entities.LoggerEntity;
import com.app.entities.UserEntity;

public interface LoggerServiceInterface {

	LoggerEntity getLoggerDetail(String token);

	void createLogger(LoggerDto loggerDto, UserEntity user);

	void logoutUser(String token, Long userId, String email);

}
