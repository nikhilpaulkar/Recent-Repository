package com.app.dto;

import java.util.Date;

public class ProjectListDto {

	public ProjectListDto() {

		super();

		// TODO Auto-generated constructor stub
	}

	public ProjectListDto(Long id, String name, String description, String responsibilities, int teamSize, String technicalStack, Date projectStartDate, Date projectEndDate, Date createdAt) {

		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.responsibilities = responsibilities;
		this.teamSize = teamSize;
		this.technicalStack = technicalStack;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.createdAt = createdAt;

	}

	private Long id;

	private String name;

	private String description;

	private String responsibilities;

	private int teamSize;

	private String technicalStack;

	private Date projectStartDate;

	private Date projectEndDate;

	private Date createdAt;

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

	public String getResponsibilities() {

		return responsibilities;

	}

	public void setResponsibilities(String responsibilities) {

		this.responsibilities = responsibilities;

	}

	public int getTeamSize() {

		return teamSize;

	}

	public void setTeamSize(int teamSize) {

		this.teamSize = teamSize;

	}

	public String getTechnicalStack() {

		return technicalStack;

	}

	public void setTechnicalStack(String technicalStack) {

		this.technicalStack = technicalStack;

	}

	public Date getProjectStartDate() {

		return projectStartDate;

	}

	public void setProjectStartDate(Date projectStartDate) {

		this.projectStartDate = projectStartDate;

	}

	public Date getProjectEndDate() {

		return projectEndDate;

	}

	public void setProjectEndDate(Date projectEndDate) {

		this.projectEndDate = projectEndDate;

	}

	public Date getCreatedAt() {

		return createdAt;

	}

	public void setCreatedAt(Date createdAt) {

		this.createdAt = createdAt;

	}

}
