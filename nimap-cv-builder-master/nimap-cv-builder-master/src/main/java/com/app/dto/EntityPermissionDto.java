package com.app.dto;

public class EntityPermissionDto {

	public EntityPermissionDto() {

		// TODO Auto-generated constructor stub
	}

	public EntityPermissionDto(Long id, String actionName, Boolean isSelected, Long entityId) {

		super();
		this.id = id;
		this.actionName = actionName;
		this.isSelected = isSelected;
		this.entityId = entityId;

	}

	private Long id;

	private String actionName;

	private Boolean isSelected;

	private Long entityId;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public String getActionName() {

		return actionName;

	}

	public void setActionName(String actionName) {

		this.actionName = actionName;

	}

	public Boolean getIsSelected() {

		return isSelected;

	}

	public void setIsSelected(Boolean isSelected) {

		this.isSelected = isSelected;

	}

	public Long getEntityId() {

		return entityId;

	}

	public void setEntityId(Long entityId) {

		this.entityId = entityId;

	}

}
