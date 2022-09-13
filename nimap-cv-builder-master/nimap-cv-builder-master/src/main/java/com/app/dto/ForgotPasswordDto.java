package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ForgotPasswordDto {

	@NotNull(message = "password is Required*passwordRequired")
	@NotEmpty(message = "password is Required*passwordRequired")
	@NotBlank(message = "password is Required*passwordRequired")
	private String password;

	@NotNull(message = "confirmpassword is Required*confirmpasswordRequired")
	@NotEmpty(message = "confirmpassword is Required*confirmpasswordRequired")
	@NotBlank(message = "confirmpassword is Required*confirmpasswordRequired")
	private String confirmpassword;

	@NotNull(message = "token is Required*tokenRequired")
	@NotEmpty(message = "token is Required*tokenRequired")
	@NotBlank(message = "token is Required*tokenRequired")
	private String token;

	public ForgotPasswordDto(@NotNull(message = "password is Required*passwordRequired") @NotEmpty(message = "password is Required*passwordRequired") @NotBlank(message = "password is Required*passwordRequired") String password, @NotNull(message = "confirmpassword is Required*confirmpasswordRequired") @NotEmpty(message = "confirmpassword is Required*confirmpasswordRequired") @NotBlank(message = "confirmpassword is Required*confirmpasswordRequired") String confirmpassword, @NotNull(message = "token is Required*tokenRequired") @NotEmpty(message = "token is Required*tokenRequired") @NotBlank(message = "token is Required*tokenRequired") String token) {

		super();
		this.password = password;
		this.confirmpassword = confirmpassword;
		this.token = token;

	}

	public ForgotPasswordDto() {

		super();

		// TODO Auto-generated constructor stub
	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public String getConfirmpassword() {

		return confirmpassword;

	}

	public void setConfirmpassword(String confirmpassword) {

		this.confirmpassword = confirmpassword;

	}

	public String getToken() {

		return token;

	}

	public void setToken(String token) {

		this.token = token;

	}

}
