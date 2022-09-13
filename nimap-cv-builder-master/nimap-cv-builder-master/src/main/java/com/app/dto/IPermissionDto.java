package com.app.dto;

public interface IPermissionDto {

	// @Value("#{target.pk.permission.id}")
	public Long getPkPermissionId();

	// @Value("#{target.pk.permission.actionName}")
	public String getPkPermissionActionName();

	// @Value("#{target.pk.permission.description}")
	public String getPkPermissionDescription();

	// @Value("#{target.pk.permission.entityId.id}")
	public Long getPkPermissionEntityIdId();

}
