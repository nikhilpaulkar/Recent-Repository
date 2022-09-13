package com.app.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AuthRequestDto implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@NotNull(message = "Email is Required*emailRequired")
	@NotEmpty(message = "Email is Required*emailRequired")
	@NotBlank(message = "Email is Required*emailRequired")
	private String email;

	@NotNull(message = "Password is Required*passwordRequired")
	@NotEmpty(message = "Password is Required*passwordRequired")
	@NotBlank(message = "Password is Required*passwordRequired")
	private String password;

	// need default constructor for JSON Parsing
	public AuthRequestDto() {

	}

	public AuthRequestDto(String email, String password) {

		super();
		this.email = email;
		this.password = password;

	}

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public static long getSerialversionuid() {

		return serialVersionUID;

	}

}