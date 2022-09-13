package com.app.dto;

import java.util.Date;

import javax.validation.constraints.*;

public class ProjectAddDto {

	public ProjectAddDto() {

		super();

		// TODO Auto-generated constructor stub
	}

	 

	
	 
	public String name;

	 
	public String description;

	 
	public String[] responsibilities;

	 
	public int teamSize;

	 
	public String[] technicalStack;

	 
	public Date projectStartAt;

	 
	
	
	public Date projectEndAt;
	
	public String duration;
	
	
	

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
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

	public String[] getResponsibilities() {

		return responsibilities;

	}

	public void setResponsibilities(String[] responsibilities) {

		this.responsibilities = responsibilities;

	}

	public int getTeamSize() {

		return teamSize;

	}

	public void setTeamSize(int teamSize) {

		this.teamSize = teamSize;

	}

	public String[] getTechnicalStack() {

		return technicalStack;

	}

	public void setTechnicalStack(String[] technicalStack) {

		this.technicalStack = technicalStack;

	}

	public Date getProjectStartAt() {

		return projectStartAt;

	}

	public void setProjectStartAt(Date projectStartAt) {

		this.projectStartAt = projectStartAt;

	}

	public Date getProjectEndAt() {

		return projectEndAt;

	}

	public void setProjectEndAt(Date projectEndAt) {

		this.projectEndAt = projectEndAt;

	}

}
