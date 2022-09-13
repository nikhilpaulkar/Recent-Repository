package com.app.dto;

import java.util.List;

public class RolePermissionDto {

	public RolePermissionDto() {

		// TODO Auto-generated constructor stub
	}

	public RolePermissionDto(Long id, String roleName, String description, List<EntityDto> entity) {

		super();
		this.id = id;
		this.roleName = roleName;
		this.description = description;
		this.entity = entity;

	}

	private Long id;

	private String roleName;

	private String description;

	private List<EntityDto> entity;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public String getRoleName() {

		return roleName;

	}

	public void setRoleName(String roleName) {

		this.roleName = roleName;

	}

	public String getDescription() {

		return description;

	}

	public void setDescription(String description) {

		this.description = description;

	}

	public List<EntityDto> getEntity() {

		return entity;

	}

	public void setEntity(List<EntityDto> entity) {

		this.entity = entity;

	}

}
