package com.app.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.app.entities.GenderEnum;
import com.app.utils.Enum;

public class UserDto {

	public UserDto() {

		// TODO Auto-generated constructor stub
	}

	private String name;

	private String summary;

	private String email;

	private String address;

	private Date careerStartDate;

	private Long designationId;

	private String laptopConfiguration;

	public String[] TechnicalStack;
	
	public String[] vendor;

	private Date dob;

//	@Enum(enumClass = GenderEnum.class, ignoreCase = true)
	private String gender;

	private String highestQualification;

	private String universityName;

	private Integer yearOfPassing;

	private Date dateOfJoining;
	

	public String[] getVendor() {
		return vendor;
	}

	public void setVendor(String[] vendor) {
		this.vendor = vendor;
	}

	public String getLaptopConfiguration() {
		return laptopConfiguration;
	}

	public void setLaptopConfiguration(String laptopConfiguration) {
		this.laptopConfiguration = laptopConfiguration;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String[] getTechnicalStack() {
		return TechnicalStack;
	}

	public void setTechnicalStack(String[] technicalStack) {
		TechnicalStack = technicalStack;
	}

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public String getAddress() {

		return address;

	}

	public void setAddress(String address) {

		this.address = address;

	}

	public Date getCareerStartDate() {

		return careerStartDate;

	}

	public void setCareerStartDate(Date careerStartDate) {

		this.careerStartDate = careerStartDate;

	}

	public Long getDesignationId() {

		return designationId;

	}

	public void setDesignationId(Long designationId) {

		this.designationId = designationId;

	}

	public Date getDob() {

		return dob;

	}

	public void setDob(Date dob) {

		this.dob = dob;

	}

	public String getGender() {

		return gender;

	}

	public void setGender(String gender) {

		this.gender = gender;

	}

	public String getHighestQualification() {

		return highestQualification;

	}

	public void setHighestQualification(String highestQualification) {

		this.highestQualification = highestQualification;

	}

	public String getUniversityName() {

		return universityName;

	}

	public void setUniversityName(String universityName) {

		this.universityName = universityName;

	}

	public Integer getYearOfPassing() {

		return yearOfPassing;

	}

	public void setYearOfPassing(Integer yearOfPassing) {

		this.yearOfPassing = yearOfPassing;

	}

	public Date getDateOfJoining() {

		return dateOfJoining;

	}

	public void setDateOfJoining(Date dateOfJoining) {

		this.dateOfJoining = dateOfJoining;

	}

}
