package com.app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.app.entities.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class UserDataDto {

	private Long userId;

	private String name;

	private String email;
	
	private String laptopConfiguration;

	private String universityName;

	private String highestQualification;

	private Integer YearOfPassing;
	
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	private Date DateOfJoining;

	Optional<String> address = Optional.empty();
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	Optional<Date> careerStartDate = Optional.empty();

	private String  designationName ;
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	Optional<Date> dob = Optional.empty();

	Optional<GenderEnum> gender = Optional.empty();

	Optional<ArrayList<UserRoleDto>> roles = Optional.empty();

	private String technicalStack;
	
	private String summary;
	
	public UserDataDto() {

	}

	public UserDataDto(Long userId, String name, String email, String universityName, String highestQualification,
			Integer yearOfPassing, Date dateOfJoining) {

		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.universityName = universityName;
		this.highestQualification = highestQualification;
		YearOfPassing = yearOfPassing;
		DateOfJoining = dateOfJoining;

	}
	
	
	
	

	 

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
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

	public String getTechnicalStack() {
		return technicalStack;
	}

	public void setTechnicalStack(String technicalStack) {
		this.technicalStack = technicalStack;
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

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public Optional<ArrayList<UserRoleDto>> getRoles() {

		return roles;

	}

	public void setRoles(ArrayList<UserRoleDto> roles) {

		this.roles = Optional.ofNullable(roles);

	}

	public Optional<String> getAddress() {

		return address;

	}

	public void setAddress(String address) {

		this.address = Optional.ofNullable(address);

	}

	public Optional<Date> getCareerStartDate() {

		return careerStartDate;

	}

	public void setCareerStartDate(Date careerStartDate) {

		this.careerStartDate = Optional.ofNullable(careerStartDate);

	}

	 

	public Optional<Date> getDob() {

		return dob;

	}

	public void setDob(Date dob) {

		this.dob = Optional.ofNullable(dob);

	}

	public Optional<GenderEnum> getGender() {

		return gender;

	}

	public void setGender(GenderEnum gender) {

		this.gender = Optional.ofNullable(gender);

	}

	public String getUniversityName() {

		return universityName;

	}

	public void setUniversityName(String universityName) {

		this.universityName = universityName;

	}

	public String getHighestQualification() {

		return highestQualification;

	}

	public void setHighestQualification(String highestQualification) {

		this.highestQualification = highestQualification;

	}

	public Integer getYearOfPassing() {

		return YearOfPassing;

	}

	public void setYearOfPassing(Integer yearOfPassing) {

		YearOfPassing = yearOfPassing;

	}

	public Date getDateOfJoining() {

		return DateOfJoining;

	}

}
