package com.app.dto;

public class UploadFileResponse {

	public UploadFileResponse() {

		super();

		// TODO Auto-generated constructor stub
	}

	public UploadFileResponse(Long id, String filename, String path) {

		super();
		this.id = id;
		this.filename = filename;
		this.path = path;

	}

	private Long id;

	private String filename;

	private String path;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public String getFilename() {

		return filename;

	}

	public void setFilename(String filename) {

		this.filename = filename;

	}

	public String getPath() {

		return path;

	}

	public void setPath(String path) {

		this.path = path;

	}

}
