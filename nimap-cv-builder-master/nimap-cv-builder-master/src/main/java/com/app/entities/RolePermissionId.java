package com.app.entities;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Embeddable
public class RolePermissionId implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public RolePermissionId() {

		// TODO Auto-generated constructor stub
	}

	public RolePermissionId(RoleEntity role, PermissionEntity permission) {

		super();
		this.role = role;
		this.permission = permission;

	}

	private RoleEntity role;

	private PermissionEntity permission;

	@ManyToOne
	public RoleEntity getRole() {

		return role;

	}

	public void setRole(RoleEntity role) {

		this.role = role;

	}

	@ManyToOne
	public PermissionEntity getPermission() {

		return permission;

	}

	public void setPermission(PermissionEntity permission) {

		this.permission = permission;

	}

	@Override
	public int hashCode() {

		return Objects.hash(permission, role);

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {

			return true;

		}

		if ((obj == null) || (getClass() != obj.getClass())) {

			return false;

		}

		RolePermissionId other = (RolePermissionId) obj;
		return Objects.equals(permission, other.permission) && Objects.equals(role, other.role);

	}

}
