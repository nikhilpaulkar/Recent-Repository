package com.app.dto;

public class DashboardDto {

	private Long userCount;

	private Long cvCount;

	public DashboardDto(Long userCount) {

		super();
		this.userCount = userCount;

	}

	public DashboardDto(Long userCount, Long cvCount) {

		super();
		this.userCount = userCount;
		this.cvCount = cvCount;

	}

	public Long getUserCount() {

		return userCount;

	}

	public void setUserCount(Long userCount) {

		this.userCount = userCount;

	}

	public Long getCvCount() {

		return cvCount;

	}

	public void setCvCount(Long cvCount) {

		this.cvCount = cvCount;

	}

}
