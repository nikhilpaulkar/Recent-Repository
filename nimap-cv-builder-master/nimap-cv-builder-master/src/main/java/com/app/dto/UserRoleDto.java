package com.app.dto;

public class UserRoleDto {

	public UserRoleDto() {

		// TODO Auto-generated constructor stub
	}

	public UserRoleDto(Long id, String name, Boolean isSelected) {

		super();
		this.id = id;
		this.name = name;
		this.isSelected = isSelected;

	}

	private Long id;

	private String name;

	private Boolean isSelected;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public Boolean getIsSelected() {

		return isSelected;

	}

	public void setIsSelected(Boolean isSelected) {

		this.isSelected = isSelected;

	}

}
