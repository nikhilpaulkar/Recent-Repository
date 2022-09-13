package com.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EditUserRequestDto extends UserDto {

	public EditUserRequestDto() {

	}

	public EditUserRequestDto(Long[] roles) {
		super();
		this.roles = roles;
	}

	private Long[] roles;

	public Long[] getRoles() {

		return roles;

	}

	public void setRoles(Long[] roles) {

		this.roles = roles;

	}

}
