package com.app.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.Forgot_password_request;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.ForgotPasswordRequestRepository;
import com.app.serviceIntf.ForgotPasswordServiceIntf;

@Service("forgotPasswordRequestServiceImpl")
public class ForgotPasswordRequestServiceImpl implements ForgotPasswordServiceIntf {

	public ForgotPasswordRequestServiceImpl() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private ForgotPasswordRequestRepository forgotPasswordRequestRepository;

	@Override
	public Forgot_password_request getRequest(String token) throws ResourceNotFoundException {

		Forgot_password_request request = forgotPasswordRequestRepository.getByTokenOrderByIdDesc(token).orElseThrow(() -> new ResourceNotFoundException("Invalid Request"));
		return request;

	}

	@Override
	public void createForgotPasswordRequest(Long userId, String token, Date expireAt) {

		Forgot_password_request newRequest = new Forgot_password_request();
		newRequest.setToken(token);
		newRequest.setUserId(userId);
		forgotPasswordRequestRepository.save(newRequest);

	}

	@Override
	public void markRequestAsSuccess(Long userId, String token) throws ResourceNotFoundException {

		Forgot_password_request request = forgotPasswordRequestRepository.getByTokenAndUserIdOrderByIdDesc(token, userId).orElseThrow(() -> new ResourceNotFoundException("Invalid Request"));
		request.setIsActive(false);
		request.setSuccessAt(new Date());
		request.setLinkUsedAt(new Date());
		forgotPasswordRequestRepository.save(request);

	}

	@Override
	public void markRequestAsExpire(Long userId, String token) throws ResourceNotFoundException {

		Forgot_password_request request = forgotPasswordRequestRepository.getByTokenAndUserIdOrderByIdDesc(token, userId).orElseThrow(() -> new ResourceNotFoundException("Invalid Request"));
		request.setIsActive(false);
		forgotPasswordRequestRepository.save(request);

	}

}
