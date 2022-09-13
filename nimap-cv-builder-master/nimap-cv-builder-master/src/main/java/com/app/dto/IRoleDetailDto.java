package com.app.dto;

import java.io.Serializable;

public interface IRoleDetailDto extends Serializable {

	public Long getId();

	public String getRoleName();

	public String getDescription();

	public Boolean getIsActive();

	public String getCreatedAt();

}
