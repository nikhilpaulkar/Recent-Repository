package com.app.dto;

import java.util.List;

public class EntityDto {

	public EntityDto() {

		// TODO Auto-generated constructor stub
	}

	public EntityDto(Long id, String entityName, Boolean isSelected, List<EntityPermissionDto> permissions) {

		super();
		this.id = id;
		this.entityName = entityName;
		this.isSelected = isSelected;
		this.permissions = permissions;

	}

	private Long id;

	private String entityName;

	private Boolean isSelected;

	private List<EntityPermissionDto> permissions;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public String getEntityName() {

		return entityName;

	}

	public void setEntityName(String entityName) {

		this.entityName = entityName;

	}

	public Boolean getIsSelected() {

		return isSelected;

	}

	public void setIsSelected(Boolean isSelected) {

		this.isSelected = isSelected;

	}

	public List<EntityPermissionDto> getPermissions() {

		return permissions;

	}

	public void setPermissions(List<EntityPermissionDto> permissions) {

		this.permissions = permissions;

	}

}
