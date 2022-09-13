package com.app.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "forgot_password_request")
public class Forgot_password_request implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Forgot_password_request() {

		super();

		// TODO Auto-generated constructor stub
	}

	public Forgot_password_request(Long id, Long userId, String token, Date linkUsedAt, Date successAt, Boolean isActive, Date createdAt) {

		super();
		this.id = id;
		this.userId = userId;
		this.token = token;
		this.linkUsedAt = linkUsedAt;
		this.successAt = successAt;
		this.isActive = isActive;
		this.createdAt = createdAt;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "token", length = 512)
	private String token;

	@Column(name = "link_used_at")
	private Date linkUsedAt;

	@Column(name = "success_at")
	private Date successAt;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public Long getUserId() {

		return userId;

	}

	public void setUserId(Long userId) {

		this.userId = userId;

	}

	public String getToken() {

		return token;

	}

	public void setToken(String token) {

		this.token = token;

	}

	public Date getLinkUsedAt() {

		return linkUsedAt;

	}

	public void setLinkUsedAt(Date linkUsedAt) {

		this.linkUsedAt = linkUsedAt;

	}

	public Date getSuccessAt() {

		return successAt;

	}

	public void setSuccessAt(Date successAt) {

		this.successAt = successAt;

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

}
