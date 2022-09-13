package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EntityRequestDto {

	public EntityRequestDto() {

		// TODO Auto-generated constructor stub
	}

	public EntityRequestDto(String entityName, String description) {

		super();
		this.entityName = entityName;
		this.description = description;

	}

	@NotBlank(message = "Entity Name is Required*entityNameRequired")
	@NotEmpty(message = "Entity Name is Required*entityNameRequired")
	@NotNull(message = "Entity Name is Required*entityNameRequired")
	private String entityName;

	@NotBlank(message = "Entity Description is Required*entityDescriptionRequired")
	@NotEmpty(message = "Entity Description is Required*entityDescriptionRequired")
	@NotNull(message = "Entity Description is Required*entityDescriptionRequired")
	private String description;

	public String getEntityName() {

		return entityName;

	}

	public void setEntityName(String entityName) {

		this.entityName = entityName;

	}

	public String getDescription() {

		return description;

	}

	public void setDescription(String description) {

		this.description = description;

	}

}
