package com.app.dto;

public class AddSettingDto {

	public AddSettingDto() {

		super();

		// TODO Auto-generated constructor stub
	}

	public AddSettingDto(String key, String value, Boolean isAdminOnly) {

		super();
		this.key = key;
		this.value = value;
		this.isAdminOnly = isAdminOnly;

	}

	private String key;

	private String value;

	private Boolean isAdminOnly;

	private String newValue;

	public String getNewValue() {

		return newValue;

	}

	public void setNewValue(String newValue) {

		this.newValue = newValue;

	}

	public String getKey() {

		return key;

	}

	public void setKey(String key) {

		this.key = key;

	}

	public String getValue() {

		return value;

	}

	public void setValue(String value) {

		this.value = value;

	}

	public Boolean getIsAdminOnly() {

		return isAdminOnly;

	}

	public void setIsAdminOnly(Boolean isAdminOnly) {

		this.isAdminOnly = isAdminOnly;

	}

}
