package com.app.dto;

import java.util.ArrayList;

import javax.validation.constraints.*;

public class ResponsibilityAddDto {

	@NotNull(message = "Langauage is Required*LangauageRequired")
	public Long codeLanguagesId;

	@NotBlank(message = "Responsibility Name is Required*descriptionNameRequired")
	@NotEmpty(message = "Responsibility Name is Required*descriptionNameRequired")
	@NotNull(message = "Responsibility Name is Required*descriptionNameRequired")
	public String name;
	
	
	public String description;

	public ResponsibilityAddDto() {

		super();

		// TODO Auto-generated constructor stub
	}

	public Long getCodeLanguagesId() {
		return codeLanguagesId;
	}

	public void setCodeLanguagesId(Long codeLanguagesId) {
		this.codeLanguagesId = codeLanguagesId;
	}

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
