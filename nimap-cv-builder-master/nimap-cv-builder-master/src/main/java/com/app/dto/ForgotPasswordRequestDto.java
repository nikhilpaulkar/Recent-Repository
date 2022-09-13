package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ForgotPasswordRequestDto {

	public ForgotPasswordRequestDto() {

		super();

		// TODO Auto-generated constructor stub
	}

	public ForgotPasswordRequestDto(@NotNull(message = "Email is Required*emailRequired") @NotEmpty(message = "Email is Required*emailRequired") @NotBlank(message = "Email is Required*emailRequired") String email) {

		super();
		this.email = email;

	}

	@NotNull(message = "Email is Required*emailRequired")
	@NotEmpty(message = "Email is Required*emailRequired")
	@NotBlank(message = "Email is Required*emailRequired")
	private String email;

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

}
