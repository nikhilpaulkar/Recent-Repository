package com.app.dto;

public class MasterListVendor {
	private String name;
	
	private String Organization;
	
	private String mobile;
	
	private String email;

	public MasterListVendor(String name, String organization, String mobile, String email) {
		super();
		this.name = name;
		Organization = organization;
		this.mobile = mobile;
		this.email = email;
	}

	public MasterListVendor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganization() {
		return Organization;
	}

	public void setOrganization(String organization) {
		Organization = organization;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
