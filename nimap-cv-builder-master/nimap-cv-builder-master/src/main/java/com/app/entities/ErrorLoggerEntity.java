package com.app.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "error_logger")
public class ErrorLoggerEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public ErrorLoggerEntity() {

		super();

		// TODO Auto-generated constructor stub
	}

	public ErrorLoggerEntity(Long id, String message, String url, MethodEnum method, String host, String body, String token, Date createdAt) {

		super();
		this.id = id;
		this.message = message;
		this.url = url;
		this.method = method;
		this.host = host;
		this.body = body;
		this.token = token;
		this.createdAt = createdAt;

	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "message", length = 100000)
	private String message;

	@Column(name = "url")
	private String url;

	@Column(name = "method")
	@Enumerated(EnumType.STRING)
	private MethodEnum method;

	@Column(name = "host")
	private String host;

	@Column(name = "body", length = 10000)
	private String body;

	@Column(name = "token", length = 512)
	private String token;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public String getMessage() {

		return message;

	}

	public void setMessage(String message) {

		this.message = message;

	}

	public String getUrl() {

		return url;

	}

	public void setUrl(String url) {

		this.url = url;

	}

	public MethodEnum getMethod() {

		return method;

	}

	public void setMethod(MethodEnum method) {

		this.method = method;

	}

	public String getHost() {

		return host;

	}

	public void setHost(String host) {

		this.host = host;

	}

	public String getBody() {

		return body;

	}

	public void setBody(String body) {

		this.body = body;

	}

	public String getToken() {

		return token;

	}

	public void setToken(String token) {

		this.token = token;

	}

	public Date getCreatedAt() {

		return createdAt;

	}

	public void setCreatedAt(Date createdAt) {

		this.createdAt = createdAt;

	}

	public static long getSerialversionuid() {

		return serialVersionUID;

	}

}
