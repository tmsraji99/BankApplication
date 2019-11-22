package com.ojas.es.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Table(name = "candidate")
@javax.persistence.Entity
@Indexed
public class Candidate implements Entity {

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "candidate_id", strategy = "com.ojas.es.util.CandidateIdGenerator")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_id")
	private Long id;
	@Column
	private Date date;
	@Column
	private String title;

	@Column
	private String status;
	@Column
	private String street1;
	@Column
	private String street2;
	@Column
	private String candidateSource;
	@Column
	private String altenateEmail;
	@Column
	private String alternateMobile;

	private String profileDate;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String mobile;
	@Column
	private String email;
	@Column
	private String gender;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column
	private String totalExperience;
	@Column
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String relevantExperience;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)

	@Column
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)

	private String currentCTC;
	@Column
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)

	private String expectedCTC;
	@Column
	private String salaryNegotiable;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)

	@Column
	private String noticePeriod;
	@Column
	private String pincode;
	@Column
	private String city;
	@Column
	private String country;
	@Column
	private String state;
	@Column
	private String willingtoRelocate;
	@Column
	private String skypeID;
	@Column
	private String currentJobType;
	@Column
	private String payRollCompanyName;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column
	private String currentCompanyName;

	private String pancardNumber;
	@Column
	private String certificationscheck;
	@Column
	private String filename;
	@Column
	private String resume;
	@Column
	private byte[] _10thMarkmemo;
	@Column
	private String _12thMarkmemo;
	@Column
	private String highestMarkmemo;
	@Column
	private String latestSalaryslips;
	@Column
	private String salaryCertificates;
	@Column
	private String bankStatements;

	@Column
	private String birthCertificate;
	@Column
	private String form16Certificate;
	@Column
	private Date doj;

	private Date submissionDate;

	private Date statusLastUpdatedDate;

	private String noOfMonths;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String appliedPossitionFor;

	private String applyingAs;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String currentLocation;
	@IndexedEmbedded
	@ManyToOne(fetch = FetchType.EAGER)
	private Location location;

	private String workpermit;
		

	public String getWorkpermit() {
		return workpermit;
	}

	public void setWorkpermit(String workpermit) {
		this.workpermit = workpermit;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Location getLocation() {
		return location;
	}
	
//	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
//    private String otherSkils;	
//
//	public String getOtherSkils() {
//		return otherSkils;
//	}
//
//	public void setOtherSkils(String otherSkils) {
//		this.otherSkils = otherSkils;
//	}


	public void setLocation(Location location) {
		this.location = location;
	}

	public String getAppliedPossitionFor() {
		return appliedPossitionFor;
	}

	public void setAppliedPossitionFor(String appliedPossitionFor) {
		this.appliedPossitionFor = appliedPossitionFor;
	}

	public String getApplyingAs() {
		return applyingAs;
	}

	public void setApplyingAs(String applyingAs) {
		this.applyingAs = applyingAs;
	}

	public String getNoOfMonths() {
		return noOfMonths;
	}

	public void setNoOfMonths(String noOfMonths) {
		this.noOfMonths = noOfMonths;
	}

	public Date getStatusLastUpdatedDate() {
		return statusLastUpdatedDate;
	}

	public void setStatusLastUpdatedDate(Date statusLastUpdatedDate) {
		this.statusLastUpdatedDate = statusLastUpdatedDate;
	}

	@IndexedEmbedded
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "skillcandidate", joinColumns = @JoinColumn(name = "candidate_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "SKILL_ID", referencedColumnName = "ID"))
	private List<Skill> skills = new ArrayList<Skill>();

/*	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "candidateEducationMap", joinColumns = @JoinColumn(name = "candidate_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "education_ID", referencedColumnName = "ID"))
	private List<AddQualification> education = new ArrayList<AddQualification>();
	*/

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public void set_10thMarkmemo(byte[] _10thMarkmemo) {
		this._10thMarkmemo = _10thMarkmemo;
	}

	public String getAltenateEmail() {
		return altenateEmail;
	}

	public void setAltenateEmail(String altenateEmail) {
		this.altenateEmail = altenateEmail;
	}

	public String getAlternateMobile() {
		return alternateMobile;
	}

	public void setAlternateMobile(String alternateMobile) {
		this.alternateMobile = alternateMobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfileDate() {
		return profileDate;
	}

	public void setProfileDate(String profileDate) {
		this.profileDate = profileDate;
	}

	public String getCandidateSource() {
		return candidateSource;
	}

	public void setCandidateSource(String candidateSource) {
		this.candidateSource = candidateSource;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public Candidate() {
		this.date = new Date();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	public String getRelevantExperience() {
		return relevantExperience;
	}

	public void setRelevantExperience(String relevantExperience) {
		this.relevantExperience = relevantExperience;
	}

	public String getCurrentCTC() {
		return currentCTC;
	}

	public void setCurrentCTC(String currentCTC) {
		this.currentCTC = currentCTC;
	}

	public String getExpectedCTC() {
		return expectedCTC;
	}

	public void setExpectedCTC(String expectedCTC) {
		this.expectedCTC = expectedCTC;
	}

	public String getSalaryNegotiable() {
		return salaryNegotiable;
	}

	public void setSalaryNegotiable(String salaryNegotiable) {
		this.salaryNegotiable = salaryNegotiable;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getWillingtoRelocate() {
		return willingtoRelocate;
	}

	public void setWillingtoRelocate(String willingtoRelocate) {
		this.willingtoRelocate = willingtoRelocate;
	}

	public String getSkypeID() {
		return skypeID;
	}

	public void setSkypeID(String skypeID) {
		this.skypeID = skypeID;
	}

	public String getCurrentJobType() {
		return currentJobType;
	}

	public void setCurrentJobType(String currentJobType) {
		this.currentJobType = currentJobType;
	}

	public String getPayRollCompanyName() {
		return payRollCompanyName;
	}

	public void setPayRollCompanyName(String payRollCompanyName) {
		this.payRollCompanyName = payRollCompanyName;
	}

	public String getCurrentCompanyName() {
		return currentCompanyName;
	}

	public void setCurrentCompanyName(String currentCompanyName) {
		this.currentCompanyName = currentCompanyName;
	}

	public String getPancardNumber() {
		return pancardNumber;
	}

	public void setPancardNumber(String pancardNumber) {
		this.pancardNumber = pancardNumber;
	}

	public String getCertificationscheck() {
		return certificationscheck;
	}

	public void setCertificationscheck(String certificationscheck) {
		this.certificationscheck = certificationscheck;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {

		this.skills = skills;
	}

/*	public List<AddQualification> getEducation() {
		return education;
	}

	public void setEducation(List<AddQualification> education) {
		this.education = education;
	}
	*/

	public byte[] get_10thMarkmemo() {
		return _10thMarkmemo;
	}

	public String get_12thMarkmemo() {
		return _12thMarkmemo;
	}

	public void set_12thMarkmemo(String _12thMarkmemo) {
		this._12thMarkmemo = _12thMarkmemo;
	}

	public String getHighestMarkmemo() {
		return highestMarkmemo;
	}

	public void setHighestMarkmemo(String highestMarkmemo) {
		this.highestMarkmemo = highestMarkmemo;
	}

	public String getLatestSalaryslips() {
		return latestSalaryslips;
	}

	public void setLatestSalaryslips(String latestSalaryslips) {
		this.latestSalaryslips = latestSalaryslips;
	}

	public String getSalaryCertificates() {
		return salaryCertificates;
	}

	public void setSalaryCertificates(String salaryCertificates) {
		this.salaryCertificates = salaryCertificates;
	}

	public String getBankStatements() {
		return bankStatements;
	}

	public void setBankStatements(String bankStatements) {
		this.bankStatements = bankStatements;
	}

	public String getBirthCertificate() {
		return birthCertificate;
	}

	public void setBirthCertificate(String birthCertificate) {
		this.birthCertificate = birthCertificate;
	}

	public String getForm16Certificate() {
		return form16Certificate;
	}

	public void setForm16Certificate(String form16Certificate) {
		this.form16Certificate = form16Certificate;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

		
	
}
