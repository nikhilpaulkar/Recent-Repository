package com.app.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "permissions")
public class PermissionEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PermissionEntity() {

		// TODO Auto-generated constructor stub
	}

	public PermissionEntity(Long id, String actionName, String description, EntityEntity entityId, String method, String baseUrl, String path, Boolean isActive, Date createdAt, Date updatedAt) {

		super();
		this.id = id;
		this.actionName = actionName;
		this.description = description;
		this.entityId = entityId;
		this.method = method;
		this.baseUrl = baseUrl;
		this.path = path;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;

	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "action_name")
	private String actionName;

	@Column(name = "description")
	private String description;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entity_id")
	private EntityEntity entityId;

	@Column(name = "method")
	private String method;
	
	@Column(name = "base_url")
	private String baseUrl;

	@Column(name = "path")
	private String path;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

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

	public String getDescription() {

		return description;

	}

	public void setDescription(String description) {

		this.description = description;

	}

	public EntityEntity getEntityId() {

		return entityId;

	}

	public void setEntityId(EntityEntity entityId) {

		this.entityId = entityId;

	}

	public String getMethod() {

		return method;

	}

	public void setMethod(String method) {

		this.method = method;

	}

	public String getBaseUrl() {

		return baseUrl;

	}

	public void setBaseUrl(String baseUrl) {

		this.baseUrl = baseUrl;

	}

	public String getPath() {

		return path;

	}

	public void setPath(String path) {

		this.path = path;

	}

	public Boolean getIsActive() {

		return isActive;

	}

	public void setIsActive(Boolean isActive) {

		this.isActive = isActive;

	}

	public Date getCreatedAt() {

		return createdAt;

	}

	public void setCreatedAt(Date createdAt) {

		this.createdAt = createdAt;

	}

	public Date getUpdatedAt() {

		return updatedAt;

	}

	public void setUpdatedAt(Date updatedAt) {

		this.updatedAt = updatedAt;

	}

}
