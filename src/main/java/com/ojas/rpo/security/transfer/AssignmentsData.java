package com.ojas.rpo.security.transfer;

public class AssignmentsData {
	
	private String clientName;
	private String requirementName;
	private String recruiterName;
	private String assignedDate;
	private Long assignId;
	private Long requirementId;
	private Long clientId;
	
	public Long getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public String getRecruiterName() {
		return recruiterName;
	}
	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}
	public String getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}
	public Long getAssignId() {
		return assignId;
	}
	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}
	
	
	
	
}
