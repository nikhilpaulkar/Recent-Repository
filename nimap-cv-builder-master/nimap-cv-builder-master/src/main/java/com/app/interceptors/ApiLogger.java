package com.app.interceptors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import com.app.dto.ErrorResponseDto;
import com.app.entities.ApiLoggerEntity;
import com.app.entities.LoggerEntity;
import com.app.serviceIntf.ApiLoggerSerivceInterface;
import com.app.serviceIntf.LoggerServiceInterface;
import com.google.gson.Gson;

@Component
public class ApiLogger implements HandlerInterceptor {

	public ApiLogger() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private LoggerServiceInterface loggerServiceInterface;

	@Autowired
	private ApiLoggerSerivceInterface apiLoggerSerivceInterface;

	Gson gson = new Gson();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String[] arr = request.getRequestURI().split("/");
		String getParam = arr[arr.length - 1];
		String getParam2 = arr[arr.length - 2];
		ArrayList<String> skipUrls = new ArrayList<>(Arrays.asList("/auth/login", "/auth/forgot-pass", "/file/downloadFile/" + getParam2 + "/" + getParam));

		if (!skipUrls.contains(request.getRequestURI())) {

			final String requestTokenHeader = request.getHeader("Authorization").split(" ")[1];
			LoggerEntity logsDetail = loggerServiceInterface.getLoggerDetail(requestTokenHeader);

			if (logsDetail == null) {

				ErrorResponseDto error = new ErrorResponseDto("You are not login User", "notLoginUser");
				String employeeJsonString = this.gson.toJson(error);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write(employeeJsonString);
				return false;

			} else {

				ApiLoggerEntity apiDetail = new ApiLoggerEntity();
				apiDetail.setUserToken(requestTokenHeader);
				apiDetail.setIpAddress(request.getRemoteAddr());
				apiDetail.setUrl(request.getRequestURI());
				apiDetail.setMethod(request.getMethod());
				apiDetail.setHost(request.getRemoteHost());
				apiDetail.setBody(request instanceof StandardMultipartHttpServletRequest ? null : request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
				apiLoggerSerivceInterface.createApiLog(apiDetail);
				return true;

			}

		} else {
			ApiLoggerEntity apiDetail = new ApiLoggerEntity();
			apiDetail.setUserToken(requestTokenHeader);
			apiDetail.setIpAddress(request.getRemoteAddr());
			apiDetail.setUrl(request.getRequestURI());
			apiDetail.setMethod(request.getMethod());
			apiDetail.setHost(request.getRemoteHost());
			apiDetail.setBody(request instanceof StandardMultipartHttpServletRequest ? null : request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
			apiLoggerSerivceInterface.createApiLog(apiDetail);
			return true;
			
			

		}

	}

}
