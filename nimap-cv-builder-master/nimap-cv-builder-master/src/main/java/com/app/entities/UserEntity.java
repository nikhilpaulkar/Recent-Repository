package com.app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserEntity() {

		super();

		// TODO Auto-generated constructor stub
	}

	public UserEntity(Long id, String name, String email, GenderEnum gender, Date dob, String address,
			DesignationMasterEntity designationId, Date careerStartDate, String password, List<UserRoleEntity> userRole,
			UserEntity createdBy, UserEntity updatedBy, Date lastLoginAt, Date createdAt, Date updatedAt,
			Boolean isActive, String highestQualification, String universityName, Integer yearOfPassing,
			Date dateOfJoining) {

		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.designationId = designationId;
		this.careerStartDate = careerStartDate;
		this.password = password;
		this.userRole = userRole;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.lastLoginAt = lastLoginAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
		this.highestQualification = highestQualification;
		this.universityName = universityName;
		this.yearOfPassing = yearOfPassing;
		this.dateOfJoining = dateOfJoining;

	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

	@Temporal(TemporalType.DATE)
	@Column(name = "dob")
	private Date dob;

	@Column(name = "address", columnDefinition = "TEXT")
	private String address;

	@Column(name = "summary", columnDefinition = "TEXT")
	private String summary;

	@Column(name = "laptopConfig", columnDefinition = "TEXT")
	private String laptopConfiguration;

	@OneToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id")
	private DesignationMasterEntity designationId;

	@Temporal(TemporalType.DATE)
	@Column(name = "career_start_date")
	private Date careerStartDate;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user", cascade = CascadeType.ALL)
	private List<UserRoleEntity> userRole;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private UserEntity createdBy;

	@Column(name = "technical_stack", length = 1000)
	private String technicalStack;

	@Column(name = "vendor", length = 1000)
	private String vendor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	private UserEntity updatedBy;

	@Column(name = "last_login_at")
	private Date lastLoginAt;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "highest_qualification")
	private String highestQualification;

	@Column(name = "university_name")
	private String universityName;

	@Column(name = "year_of_passing")
	private Integer yearOfPassing;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_joining")
	private Date dateOfJoining;

	public String getLaptopConfiguration() {
		return laptopConfiguration;
	}

	public void setLaptopConfiguration(String laptopConfiguration) {
		this.laptopConfiguration = laptopConfiguration;
	}

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

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public GenderEnum getGender() {

		return gender;

	}

	public void setGender(GenderEnum gender) {

		this.gender = gender;

	}

	public Date getDob() {

		return dob;

	}

	public void setDob(Date dob) {

		this.dob = dob;

	}

	public String getAddress() {

		return address;

	}

	public void setAddress(String address) {

		this.address = address;

	}

	public DesignationMasterEntity getDesignationId() {

		return designationId;

	}

	public void setDesignationId(DesignationMasterEntity designationId) {

		this.designationId = designationId;

	}

	public Date getCareerStartDate() {

		return careerStartDate;

	}

	public void setCareerStartDate(Date careerStartDate) {

		this.careerStartDate = careerStartDate;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public List<UserRoleEntity> getUserRole() {

		return userRole;

	}

	public void setUserRole(List<UserRoleEntity> userRole) {

		this.userRole = userRole;

	}

	public UserEntity getCreatedBy() {

		return createdBy;

	}

	public void setCreatedBy(UserEntity createdBy) {

		this.createdBy = createdBy;

	}

	public UserEntity getUpdatedBy() {

		return updatedBy;

	}

	public void setUpdatedBy(UserEntity updatedBy) {

		this.updatedBy = updatedBy;

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

	public Boolean getIsActive() {

		return isActive;

	}

	public void setIsActive(Boolean isActive) {

		this.isActive = isActive;

	}

	public Date getLastLoginAt() {

		return lastLoginAt;

	}

	public void setLastLoginAt(Date lastLoginAt) {

		this.lastLoginAt = lastLoginAt;

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

	public String getTechnicalStack() {
		return technicalStack;
	}

	public void setTechnicalStack(String technicalStack) {
		this.technicalStack = technicalStack;
	}

	@Override
	public String toString() {

		return "{id:" + id + ",name:" + "'" + name.toString() + "'" + ",email:" + "'" + email.toString() + "'" + "}";

	}

}
