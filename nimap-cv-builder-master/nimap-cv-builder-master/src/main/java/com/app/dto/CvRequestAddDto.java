package com.app.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

public class CvRequestAddDto {
	@NotNull(message = "userName is Required*userNameRequired")
	private Long userId;

 
	public CvRequestAddDto() {

		super();

		// TODO Auto-generated constructor stub
	}

	public CvRequestAddDto(ArrayList<Long> frameworkId, ArrayList<Long> codeLanguageId, ArrayList<Long> ideId, ArrayList<Long> versionControlSystemId, ArrayList<Long> externalComponentId, ArrayList<Long> webServiceId, ArrayList<Long> otherSkillId, ArrayList<Long> operatingSystemId, ArrayList<Long> toolId) {

		super();
		this.frameworkId = frameworkId;
		this.codeLanguageId = codeLanguageId;
		this.ideId = ideId;
		this.versionControlSystemId = versionControlSystemId;
		this.externalComponentId = externalComponentId;
		this.webServiceId = webServiceId;
		this.otherSkillId = otherSkillId;
		this.operatingSystemId = operatingSystemId;
		this.toolId = toolId;

	}
 
	
	@NotNull(message = "framework is Required*frameworkRequired")
	private ArrayList<Long> frameworkId;
  
	@NotNull(message = "codeLanguage is Required*codeLanguageRequired")
	private ArrayList<Long> codeLanguageId;
	
	@NotNull(message = "ide is Required*ideRequired")
	private ArrayList<Long> ideId;
	
	@NotNull(message = "versionControlSystem is Required*versionControlSystemRequired")
	private ArrayList<Long> versionControlSystemId;
	
	@NotNull(message = "externalComponent is Required*externalComponentRequired")
	private ArrayList<Long> externalComponentId;
	
	@NotNull(message = "webService is Required*webServiceRequired")
	private ArrayList<Long> webServiceId;
	
	@NotNull(message = "otherSkill is Required*otherSkillRequired")
	private ArrayList<Long> otherSkillId;
	
	@NotNull(message = "operatingSystem is Required*operatingSystemRequired")
	private ArrayList<Long> operatingSystemId;

	@NotNull(message = "tool is Required*toolRequired")
	private ArrayList<Long> toolId;
	
	
	

	 

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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

	public ArrayList<Long> getToolId() {
		return toolId;
	}

	public void setToolId(ArrayList<Long> toolId) {
		this.toolId = toolId;
	}

}
