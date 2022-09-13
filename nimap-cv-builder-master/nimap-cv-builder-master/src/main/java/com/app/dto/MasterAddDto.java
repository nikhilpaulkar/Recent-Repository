package com.app.dto;

public class MasterAddDto {

	public MasterAddDto() {

		super();

		// TODO Auto-generated constructor stub
	}

	public MasterAddDto(String name, String description) {

		super();
		this.name = name;
		this.description = description;

	}

	public String name;

	public String description;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getDescription() {

		return description;

	}

	public void setDescription(String description) {

		this.description = description;

	}

}
