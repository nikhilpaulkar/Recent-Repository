package com.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_credential")
public class EmailCredentialEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public EmailCredentialEntity() {

		super();

		// TODO Auto-generated constructor stub
	}

	public EmailCredentialEntity(Long id, String env, String host, int port, String username, String password, Boolean smtpAuth, Boolean tlsEnable) {

		super();
		this.id = id;
		this.env = env;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.smtpAuth = smtpAuth;
		this.tlsEnable = tlsEnable;

	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "env")
	private String env;

	@Column(name = "host")
	private String host;

	@Column(name = "port")
	private int port;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "smtp_auth")
	private Boolean smtpAuth;

	@Column(name = "tls_enable")
	private Boolean tlsEnable;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public String getEnv() {

		return env;

	}

	public void setEnv(String env) {

		this.env = env;

	}

	public String getHost() {

		return host;

	}

	public void setHost(String host) {

		this.host = host;

	}

	public int getPort() {

		return port;

	}

	public void setPort(int port) {

		this.port = port;

	}

	public String getUsername() {

		return username;

	}

	public void setUsername(String username) {

		this.username = username;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public Boolean getSmtpAuth() {

		return smtpAuth;

	}

	public void setSmtpAuth(Boolean smtpAuth) {

		this.smtpAuth = smtpAuth;

	}

	public Boolean getTlsEnable() {

		return tlsEnable;

	}

	public void setTlsEnable(Boolean tlsEnable) {

		this.tlsEnable = tlsEnable;

	}

}
