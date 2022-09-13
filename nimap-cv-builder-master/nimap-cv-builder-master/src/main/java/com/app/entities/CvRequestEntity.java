package com.app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "cv_request")
public class CvRequestEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CvRequestEntity() {

		super();

		// TODO Auto-generated constructor stub
	}

	public CvRequestEntity(Long id, UserEntity userId, ArrayList<Long> frameworkId, ArrayList<Long> codeLanguageId, ArrayList<Long> ideId, ArrayList<Long> versionControlSystemId, ArrayList<Long> externalComponentId, ArrayList<Long> webServiceId, ArrayList<Long> otherSkillId, ArrayList<Long> operatingSystemId, ArrayList<Long> projectExperienceId, ArrayList<Long> toolId, String path, UserEntity createdBy, Boolean isActive, Date createdAt, Date updatedAt) {

		super();
		this.id = id;
		this.userId = userId;
		this.frameworkId = frameworkId;
		this.codeLanguageId = codeLanguageId;
		this.ideId = ideId;
		this.versionControlSystemId = versionControlSystemId;
		this.externalComponentId = externalComponentId;
		this.webServiceId = webServiceId;
		this.otherSkillId = otherSkillId;
		this.operatingSystemId = operatingSystemId;
		this.projectExperienceId = projectExperienceId;
		this.toolId = toolId;
		this.path = path;
		this.createdBy = createdBy;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;

	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",unique = true)
	private UserEntity userId;

	@Column(name = "code_language_id")
	// @ElementCollection
	private ArrayList<Long> frameworkId;

	@Column(name = "framework_id")
	// @ElementCollection
	private ArrayList<Long> codeLanguageId;

	@Column(name = "ide_id")
	// @ElementCollection
	private ArrayList<Long> ideId;

	@Column(name = "version_control_system_id")
	// @ElementCollection
	private ArrayList<Long> versionControlSystemId;

	@Column(name = "external_component_id")
	// @ElementCollection
	private ArrayList<Long> externalComponentId;

	@Column(name = "web_service_id")
	// @ElementCollection
	private ArrayList<Long> webServiceId;

	@Column(name = "other_skill_id")
	// @ElementCollection
	private ArrayList<Long> otherSkillId;

	@Column(name = "operating_system_id")
	// @ElementCollection
	private ArrayList<Long> operatingSystemId;

	@Column(name = "project_experience_id")
	// @ElementCollection
	private ArrayList<Long> projectExperienceId;

	@Column(name = "tool_id")
	// @ElementCollection
	private ArrayList<Long> toolId;

	@Column(name = "path")
	private String path;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private UserEntity createdBy;
  	
	@Column(name = "is_active")
	private Boolean isActive = true;

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

	public UserEntity getUserId() {

		return userId;

	}

	public void setUserId(UserEntity userId) {

		this.userId = userId;

	}

	public ArrayList<Long> getFrameworkId() {

		return frameworkId;

	}

	public void setFrameworkId(ArrayList<Long> frameworkId) {

		this.frameworkId = frameworkId;

	}

	public ArrayList<Long> getCodeLanguageId() {

		return codeLanguageId;

	}

	public void setCodeLanguageId(ArrayList<Long> codeLanguageId) {

		this.codeLanguageId = codeLanguageId;

	}

	public ArrayList<Long> getIdeId() {

		return ideId;

	}

	public void setIdeId(ArrayList<Long> ideId) {

		this.ideId = ideId;

	}

	public ArrayList<Long> getVersionControlSystemId() {

		return versionControlSystemId;

	}

	public void setVersionControlSystemId(ArrayList<Long> versionControlSystemId) {

		this.versionControlSystemId = versionControlSystemId;

	}

	public ArrayList<Long> getExternalComponentId() {

		return externalComponentId;

	}

	public void setExternalComponentId(ArrayList<Long> externalComponentId) {

		this.externalComponentId = externalComponentId;

	}

	public ArrayList<Long> getWebServiceId() {

		return webServiceId;

	}

	public void setWebServiceId(ArrayList<Long> webServiceId) {

		this.webServiceId = webServiceId;

	}

	public ArrayList<Long> getOtherSkillId() {

		return otherSkillId;

	}

	public void setOtherSkillId(ArrayList<Long> otherSkillId) {

		this.otherSkillId = otherSkillId;

	}

	public ArrayList<Long> getOperatingSystemId() {

		return operatingSystemId;

	}

	public void setOperatingSystemId(ArrayList<Long> operatingSystemId) {

		this.operatingSystemId = operatingSystemId;

	}

	public ArrayList<Long> getProjectExperienceId() {

		return projectExperienceId;

	}

	public void setProjectExperienceId(ArrayList<Long> projectExperienceId) {

		this.projectExperienceId = projectExperienceId;

	}

	public ArrayList<Long> getToolId() {

		return toolId;

	}

	public void setToolId(ArrayList<Long> toolId) {

		this.toolId = toolId;

	}

	public String getPath() {

		return path;

	}

	public void setPath(String path) {

		this.path = path;

	}

	public UserEntity getCreatedBy() {

		return createdBy;

	}

	public void setCreatedBy(UserEntity createdBy) {

		this.createdBy = createdBy;

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
