package com.ojas.rpo.security.entity;

import java.sql.Clob;
import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "resouceEntity")
@javax.persistence.Entity
public class ResourceEntity implements Entity {

	/**
	 * 
	 */
	@Id
	@GeneratedValue
	private Long id;
	private String requirementName;
	private String datedateofapplication;
	private String sourceOfApplication;
	private String firstName;
	private String emailID;
	private Long phoneNumber;
	private String currentLocation;
	private String preferredLocations;
	private String totalExperience;
	private String currCompanyname;
	private String currCompanyDesignation;
	private String functionalArea;
	private String role;
	private String industry;
	private Clob keySkills;
	private Double annualSalary;
	private Long noticePeriod;
	private String resumeHeadline;
	private String Summary;
	private String underGraduationdegree;
	private String ugSpecialization;
	private String ugUniversity;
	private String instituteName;
	private String ugGraduation;
	private String Postgraduation;
	private String degree;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public String getDatedateofapplication() {
		return datedateofapplication;
	}
	public void setDatedateofapplication(String datedateofapplication) {
		this.datedateofapplication = datedateofapplication;
	}
	public String getSourceOfApplication() {
		return sourceOfApplication;
	}
	public void setSourceOfApplication(String sourceOfApplication) {
		this.sourceOfApplication = sourceOfApplication;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getPreferredLocations() {
		return preferredLocations;
	}
	public void setPreferredLocations(String preferredLocations) {
		this.preferredLocations = preferredLocations;
	}
	public String getTotalExperience() {
		return totalExperience;
	}
	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}
	public String getCurrCompanyname() {
		return currCompanyname;
	}
	public void setCurrCompanyname(String currCompanyname) {
		this.currCompanyname = currCompanyname;
	}
	public String getCurrCompanyDesignation() {
		return currCompanyDesignation;
	}
	public void setCurrCompanyDesignation(String currCompanyDesignation) {
		this.currCompanyDesignation = currCompanyDesignation;
	}
	public String getFunctionalArea() {
		return functionalArea;
	}
	public void setFunctionalArea(String functionalArea) {
		this.functionalArea = functionalArea;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public Clob getKeySkills() {
		return keySkills;
	}
	public void setKeySkills(Clob keySkills) {
		this.keySkills = keySkills;
	}
	public Double getAnnualSalary() {
		return annualSalary;
	}
	public void setAnnualSalary(Double annualSalary) {
		this.annualSalary = annualSalary;
	}
	public Long getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(Long noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getResumeHeadline() {
		return resumeHeadline;
	}
	public void setResumeHeadline(String resumeHeadline) {
		this.resumeHeadline = resumeHeadline;
	}
	public String getSummary() {
		return Summary;
	}
	public void setSummary(String summary) {
		Summary = summary;
	}
	public String getUnderGraduationdegree() {
		return underGraduationdegree;
	}
	public void setUnderGraduationdegree(String underGraduationdegree) {
		this.underGraduationdegree = underGraduationdegree;
	}
	public String getUgSpecialization() {
		return ugSpecialization;
	}
	public void setUgSpecialization(String ugSpecialization) {
		this.ugSpecialization = ugSpecialization;
	}
	public String getUgUniversity() {
		return ugUniversity;
	}
	public void setUgUniversity(String ugUniversity) {
		this.ugUniversity = ugUniversity;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getUgGraduation() {
		return ugGraduation;
	}
	public void setUgGraduation(String ugGraduation) {
		this.ugGraduation = ugGraduation;
	}
	public String getPostgraduation() {
		return Postgraduation;
	}
	public void setPostgraduation(String postgraduation) {
		Postgraduation = postgraduation;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getPgspecialization() {
		return pgspecialization;
	}
	public void setPgspecialization(String pgspecialization) {
		this.pgspecialization = pgspecialization;
	}
	public String getPguniversity() {
		return pguniversity;
	}
	public void setPguniversity(String pguniversity) {
		this.pguniversity = pguniversity;
	}
	public String getInstitutename() {
		return institutename;
	}
	public void setInstitutename(String institutename) {
		this.institutename = institutename;
	}
	public String getPgGgraduationyear() {
		return pgGgraduationyear;
	}
	public void setPgGgraduationyear(String pgGgraduationyear) {
		this.pgGgraduationyear = pgGgraduationyear;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public Long getPinCode() {
		return pinCode;
	}
	public void setPinCode(Long pinCode) {
		this.pinCode = pinCode;
	}
	public String getWorkpermitforUSA() {
		return workpermitforUSA;
	}
	public void setWorkpermitforUSA(String workpermitforUSA) {
		this.workpermitforUSA = workpermitforUSA;
	}
	public Date getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	private String pgspecialization;
	private String pguniversity;
	private String institutename;
	private String pgGgraduationyear;
	private String gender;
	private String maritalStatus;
	private String City;
	private Long pinCode;
	private String workpermitforUSA;
	private Date dateofBirth;
	private String permanentAddress;

}
