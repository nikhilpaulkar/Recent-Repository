package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChangePasswordDto {

	@NotBlank(message = "password is Required*passwordRequired")
	@NotEmpty(message = "password is Required*passwordRequired")
	@NotNull(message = "password is Required*passwordRequired")
	private String password;

	@NotBlank(message = "newPassword is Required*newPasswordRequired")
	@NotEmpty(message = "newPassword is Required*newPasswordRequired")
	@NotNull(message = "newPassword is Required*newPasswordRequired")
	private String newPassword;
	
	@NotBlank(message = "confPassword is Required*confPasswordRequired")
	@NotEmpty(message = "confPassword is Required*confPasswordRequired")
	@NotNull(message = "confPassword is Required*confPasswordRequired")
	private String confPassword;

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public String getNewPassword() {

		return newPassword;

	}

	public void setNewPassword(String newPassword) {

		this.newPassword = newPassword;

	}

	public String getConfPassword() {
		return confPassword;
	}

	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}
	
	
	

}
