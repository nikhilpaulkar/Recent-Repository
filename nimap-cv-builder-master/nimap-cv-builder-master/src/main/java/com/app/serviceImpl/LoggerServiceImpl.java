package com.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.LoggerDto;
import com.app.entities.LoggerEntity;
import com.app.entities.UserEntity;
import com.app.repositories.LoggerRepository;
import com.app.serviceIntf.LoggerServiceInterface;
import com.app.utils.CacheOperation;

@Service("LoggerServiceImpl")
public class LoggerServiceImpl implements LoggerServiceInterface {

	public LoggerServiceImpl() {

		// TODO Auto-generated constructor stub
	}
	// @Autowired
	// private EntityManagerFactory entityManagerFactory;
	//
	// @Autowired
	// private UserServiceInterface userServiceInterface;

	@Autowired
	private LoggerRepository loggerRepository;

	@Autowired
	private CacheOperation cache;

	@Override
	public LoggerEntity getLoggerDetail(String token) {

		LoggerEntity logger;

		if (!cache.isKeyExist(token, token)) {

			logger = loggerRepository.findByToken(token);
			cache.addInCache(token, token, logger);

		} else {

			logger = (LoggerEntity) cache.getFromCache(token, token);

		}

		return logger;// loggerRepository.findByToken(token);

	}

	@Override
	public void createLogger(LoggerDto loggerDto, UserEntity user) {

		LoggerEntity logger = new LoggerEntity();
		logger.setUserId(user);
		logger.setToken(loggerDto.getToken());
		logger.setExpireAt(loggerDto.getExpireAt());
		loggerRepository.save(logger);

	}

	@Transactional
	@Override
	public void logoutUser(String token, Long userId, String email) {

		final String userToken = token.substring(7);
		cache.removeKeyFromCache(userToken);
		cache.removeKeyFromCache(userId + "permission");
		cache.removeKeyFromCache(email);
		loggerRepository.removeByToken(userToken);

	}

}
