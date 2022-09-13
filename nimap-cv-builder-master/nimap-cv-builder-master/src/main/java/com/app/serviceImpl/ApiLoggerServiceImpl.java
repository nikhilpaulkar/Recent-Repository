package com.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.ApiLoggerEntity;
import com.app.repositories.ApiLoggerRepository;
import com.app.serviceIntf.ApiLoggerSerivceInterface;

@Service("apiLoggerServiceImpl")
public class ApiLoggerServiceImpl implements ApiLoggerSerivceInterface {

	public ApiLoggerServiceImpl() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private ApiLoggerRepository apiLoggerRepository;

	@Override
	public void createApiLog(ApiLoggerEntity api) {

		apiLoggerRepository.save(api);

	}

}
