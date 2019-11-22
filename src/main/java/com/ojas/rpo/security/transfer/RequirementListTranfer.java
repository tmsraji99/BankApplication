package com.ojas.rpo.security.transfer;

import java.util.Date;

public class RequirementListTranfer {

	Long id;
	Long assignId;
	Long clientId;
	String nameOfRequirement;
	String clientName;
	Date startDate;
	Date endDate;
	String contact_Name;
	String mobile;
	String status;
	String locationName;
	String email;
	String noticePeriod;
	String leadName;
	Date assigenedDate;
	private Date lastUpdatedDate;

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Date getAssigenedDate() {
		return assigenedDate;
	}

	public void setAssigenedDate(Date assigenedDate) {
		this.assigenedDate = assigenedDate;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameOfRequirement() {
		return nameOfRequirement;
	}

	public void setNameOfRequirement(String nameOfRequirement) {
		this.nameOfRequirement = nameOfRequirement;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getContact_Name() {
		return contact_Name;
	}

	public void setContact_Name(String contact_Name) {
		this.contact_Name = contact_Name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getAssignId() {
		return assignId;
	}

	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

}
