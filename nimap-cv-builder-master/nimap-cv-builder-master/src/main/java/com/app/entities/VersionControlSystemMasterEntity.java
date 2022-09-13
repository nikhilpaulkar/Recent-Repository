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
@Table(name = "version_control_system_master")
public class VersionControlSystemMasterEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public VersionControlSystemMasterEntity() {

		super();

		// TODO Auto-generated constructor stub
	}

	public VersionControlSystemMasterEntity(Long id, String name, String description, UserEntity createdBy, UserEntity updatedBy, Boolean isActive, Date createdAt, Date updatedAt) {

		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;

	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private UserEntity createdBy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	private UserEntity updatedBy;

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

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getDescription() {

		return description;

	}

	public void setDescription(String description) {

		this.description = description;

	}

	public UserEntity getCreatedBy() {

		return createdBy;

	}

	public void setCreatedBy(UserEntity createdBy) {

		this.createdBy = createdBy;

	}

	public UserEntity getUpdatedBy() {

		return updatedBy;

	}

	public void setUpdatedBy(UserEntity updatedBy) {

		this.updatedBy = updatedBy;

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
