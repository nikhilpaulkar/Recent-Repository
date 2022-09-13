package com.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_setting")
public class AppSettingsEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AppSettingsEntity() {

		super();

		// TODO Auto-generated constructor stub
	}

	public AppSettingsEntity(Long id, String key, String value, Boolean isAdminOnly, Boolean isActive) {

		super();
		this.id = id;
		this.key = key;
		this.value = value;
		this.isAdminOnly = isAdminOnly;
		this.isActive = isActive;

	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "key")
	private String key;

	@Column(name = "value")
	private String value;

	@Column(name = "is_admin_only")
	private Boolean isAdminOnly = true;

	@Column(name = "is_active")
	private Boolean isActive = true;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

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

	public Boolean getIsActive() {

		return isActive;

	}

	public void setIsActive(Boolean isActive) {

		this.isActive = isActive;

	}

}
