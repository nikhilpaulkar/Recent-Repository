package com.app.serviceIntf;

import java.util.Date;

import com.app.entities.Forgot_password_request;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface ForgotPasswordServiceIntf {

	public Forgot_password_request getRequest(String token) throws ResourceNotFoundException;

	public void createForgotPasswordRequest(Long userId, String token, Date expireAt);

	public void markRequestAsSuccess(Long userId, String token) throws ResourceNotFoundException;

	public void markRequestAsExpire(Long userId, String token) throws ResourceNotFoundException;

}
