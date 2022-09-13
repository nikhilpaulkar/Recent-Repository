package com.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public RoleEntity() {

		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "description")
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.role", cascade = CascadeType.ALL)
	private List<UserRoleEntity> userRole;

	public RoleEntity(Long id, String roleName, String description, List<UserRoleEntity> userRole, Boolean isActive, UserEntity createdBy, UserEntity updatedBy, Date createdAt, Date updatedAt) {

		super();
		this.id = id;
		this.roleName = roleName;
		this.description = description;
		this.userRole = userRole;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;

	}

	public List<UserRoleEntity> getUserRole() {

		return userRole;

	}

	public void setUserRole(List<UserRoleEntity> userRole) {

		this.userRole = userRole;

	}

	@Column(name = "is_active")
	private Boolean isActive = true;

	@OneToOne
	@JoinColumn(name = "created_by")
	private UserEntity createdBy;

	@OneToOne
	@JoinColumn(name = "updated_by")
	private UserEntity updatedBy;

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

	public String getRoleName() {

		return roleName;

	}

	public void setRoleName(String roleName) {

		this.roleName = roleName;

	}

	public String getDescription() {

		return description;

	}

	public void setDescription(String description) {

		this.description = description;

	}

	public Boolean getIsActive() {

		return isActive;

	}

	public void setIsActive(Boolean isActive) {

		this.isActive = isActive;

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
