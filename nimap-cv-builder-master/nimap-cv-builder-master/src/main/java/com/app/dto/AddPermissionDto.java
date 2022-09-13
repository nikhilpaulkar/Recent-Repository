package com.app.dto;

import com.app.entities.RolePermissionId;

public class AddPermissionDto {

	public AddPermissionDto() {

		// TODO Auto-generated constructor stub
	}

	public AddPermissionDto(RolePermissionId pk) {

		this.pk = pk;

	}

	private RolePermissionId pk;

	public RolePermissionId getPk() {

		return pk;

	}

	public void setPk(RolePermissionId pk) {

		this.pk = pk;

	}

}
