package com.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddPermissionRequestDto {

	public AddPermissionRequestDto() {

		// TODO Auto-generated constructor stub
	}

	public AddPermissionRequestDto(Long[] permissions) {

		super();
		this.permissions = permissions;

	}

	@NotEmpty(message = "Permission is Required*permissionRequired")
	@NotNull(message = "Permission is Required*permissionRequired")
	private Long[] permissions;

	public Long[] getPermissions() {

		return permissions;

	}

	public void setPermissions(Long[] permissions) {

		this.permissions = permissions;

	}

}
