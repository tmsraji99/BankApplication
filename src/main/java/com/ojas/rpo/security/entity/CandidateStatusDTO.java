package com.ojas.rpo.security.entity;

public class CandidateStatusDTO {
	
	private Long candidateId;
	private Long requirementId;
	private Long loginId;
	private String status;
	private String reason;
	private String candidateStatus;
	
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public Long getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCandidateStatus() {
		return candidateStatus;
	}
	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}
	
	

}
