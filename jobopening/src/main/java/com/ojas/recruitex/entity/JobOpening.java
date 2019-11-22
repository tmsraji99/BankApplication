package com.ojas.recruitex.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class JobOpening implements com.ojas.recruitex.entity.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long openingId;
	private String positionTitile;
	private String contactName;
	private String assignedRecruiter;
	private Date targetDate;
	private String status;
	private String postelCode;
	private String country;
	private String salary;
	private String clientName;
	private String accountmananger;
	private String jobType;
	@NotEmpty
	private String city;
	private String state;
	private String workExperience;
	private String skillSet;

	public JobOpening() {
		super();
	}

	public Long getOpeningId() {
		return openingId;
	}

	public void setOpeningId(Long openingId) {
		this.openingId = openingId;
	}

	public String getPositionTitile() {
		return positionTitile;
	}

	public void setPositionTitile(String positionTitile) {
		this.positionTitile = positionTitile;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getAssignedRecruiter() {
		return assignedRecruiter;
	}

	public void setAssignedRecruiter(String assignedRecruiter) {
		this.assignedRecruiter = assignedRecruiter;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPostelCode() {
		return postelCode;
	}

	public void setPostelCode(String postelCode) {
		this.postelCode = postelCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAccountmananger() {
		return accountmananger;
	}

	public void setAccountmananger(String accountmananger) {
		this.accountmananger = accountmananger;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}

	public JobOpening(Long openingId, String positionTitile, String contactName, String assignedRecruiter,
			Date targetDate, String status, String postelCode, String country, String salary, String clientName,
			String accountmananger, String jobType, String city, String state, String workExperience, String skillSet) {
		super();
		this.openingId = openingId;
		this.positionTitile = positionTitile;
		this.contactName = contactName;
		this.assignedRecruiter = assignedRecruiter;
		this.targetDate = targetDate;
		this.status = status;
		this.postelCode = postelCode;
		this.country = country;
		this.salary = salary;
		this.clientName = clientName;
		this.accountmananger = accountmananger;
		this.jobType = jobType;
		this.city = city;
		this.state = state;
		this.workExperience = workExperience;
		this.skillSet = skillSet;
	}

	public JobOpening(String positionTitile, String contactName, String assignedRecruiter, Date targetDate,
			String status, String postelCode, String country, String salary, String clientName, String accountmananger,
			String jobType, String city, String state, String workExperience, String skillSet) {
		super();
		this.positionTitile = positionTitile;
		this.contactName = contactName;
		this.assignedRecruiter = assignedRecruiter;
		this.targetDate = targetDate;
		this.status = status;
		this.postelCode = postelCode;
		this.country = country;
		this.salary = salary;
		this.clientName = clientName;
		this.accountmananger = accountmananger;
		this.jobType = jobType;
		this.city = city;
		this.state = state;
		this.workExperience = workExperience;
		this.skillSet = skillSet;
	}

	@Override
	public String toString() {
		return "JobOpening [openingId=" + openingId + ", positionTitile=" + positionTitile + ", contactName="
				+ contactName + ", assignedRecruiter=" + assignedRecruiter + ", targetDate=" + targetDate + ", status="
				+ status + ", postelCode=" + postelCode + ", country=" + country + ", salary=" + salary
				+ ", clientName=" + clientName + ", accountmananger=" + accountmananger + ", jobType=" + jobType
				+ ", city=" + city + ", state=" + state + ", workExperience=" + workExperience + ", skillSet="
				+ skillSet + "]";
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
