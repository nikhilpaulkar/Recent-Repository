package com.app.dto;

public class RequestInfoDto {

	public RequestInfoDto() {

		// TODO Auto-generated constructor stub
	}

	public RequestInfoDto(String method, String baseUrl, String path) {

		super();
		this.method = method;
		this.baseUrl = baseUrl;
		this.path = path;

	}

	private String method;

	private String baseUrl;

	private String path;

	public String getMethod() {

		return method;

	}

	public void setMethod(String method) {

		this.method = method;

	}

	public String getBaseUrl() {

		return baseUrl;

	}

	public void setBaseUrl(String baseUrl) {

		this.baseUrl = baseUrl;

	}

	public String getPath() {

		return path;

	}

	public void setPath(String path) {

		this.path = path;

	}

}
