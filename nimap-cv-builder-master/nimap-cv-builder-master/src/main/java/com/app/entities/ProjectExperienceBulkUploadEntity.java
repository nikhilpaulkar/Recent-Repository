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
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "project_experience_bulk_upload")
public class ProjectExperienceBulkUploadEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ProjectExperienceBulkUploadEntity() {

		super();

		// TODO Auto-generated constructor stub
	}

	public ProjectExperienceBulkUploadEntity(Long id, Long fileId, Long userId, String name, String description, String responsibilities, int teamSize, String technicalStack, Date projectStartDate, Date projectEndDate, String status, String message, Long createdBy, Long updatedBy, Boolean isActive, Date createdAt, Date updatedAt) {

		super();
		this.id = id;
		this.fileId = fileId;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.responsibilities = responsibilities;
		this.teamSize = teamSize;
		this.technicalStack = technicalStack;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.status = status;
		this.message = message;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;

	}
	/*
	//
	public ProjectExperienceBulkUploadEntity(Long id, Long fileId, Long userId, String name, String description,
			String responsibilities, int teamSize, String technicalStack, Date projectStartDate, Date projectEndDate,
			String status, String message, Long createdBy, Long updatedBy, Boolean isActive, Date createdAt,
			Date updatedAt, String duration) {
		super();
		this.id = id;
		this.fileId = fileId;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.responsibilities = responsibilities;
		this.teamSize = teamSize;
		this.technicalStack = technicalStack;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.status = status;
		this.message = message;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.duration = duration;
	}
	
	
	//

	public String getduration() {
		return duration;
	}

	public void setduration(String duration) {
		this.duration = duration;
	}
*/

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	

	@Column(name = "file_id")
	private Long fileId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "name")
	private String name;

	@Column(name = "description", length = 10000)
	private String description;

	@Column(name = "responsibilities", length = 1000)
	private String responsibilities;

	@Column(name = "team_size")
	private int teamSize;

	@Column(name = "technical_stack", length = 1000)
	private String technicalStack;

	@Column(name = "project_start_date")
	private Date projectStartDate;

	@Column(name = "project_end_date")
	private Date projectEndDate;

	@Column(name = "status")
	private String status;

	@Column(name = "message")
	private String message;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;
	//@Column(name = "duration")
	//private String duration;
      

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public Long getFileId() {

		return fileId;

	}

	public void setFileId(Long fileId) {

		this.fileId = fileId;

	}

	public Long getUserId() {

		return userId;

	}

	public void setUserId(Long userId) {

		this.userId = userId;

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

	public String getStatus() {

		return status;

	}

	public void setStatus(String status) {

		this.status = status;

	}

	public String getMessage() {

		return message;

	}

	public void setMessage(String message) {

		this.message = message;

	}

	public Long getCreatedBy() {

		return createdBy;

	}

	public void setCreatedBy(Long createdBy) {

		this.createdBy = createdBy;

	}

	public Long getUpdatedBy() {

		return updatedBy;

	}

	public void setUpdatedBy(Long updatedBy) {

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
