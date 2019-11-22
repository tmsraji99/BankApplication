package com.ojas.rpo.security.transfer;

import java.util.Date;

public class ClientsListTransfer {

	private Long id;
	private String clientName;
	private String customerType;
	private Date startDate;
	private String primaryContact;
	private String secondaryContact;
	private String leadName;
	private Date lastUpdatedDate;
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getPrimaryContact() {
		return primaryContact;
	}
	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}
	public String getSecondaryContact() {
		return secondaryContact;
	}
	public void setSecondaryContact(String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}
	
	
	
	
}
