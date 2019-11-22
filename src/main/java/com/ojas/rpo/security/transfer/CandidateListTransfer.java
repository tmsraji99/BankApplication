package com.ojas.rpo.security.transfer;

import java.util.Date;

public class CandidateListTransfer {
	
	Long candidateId;
	String candidateName;
	String email;
	String mobile;
	String skypeId;
	String totalExperience;
	Date submissionDate;
	String state;
	String candidateStatus;
	
	Date statusLastUpdatedDate;
	
	public Date getStatusLastUpdatedDate() {
		return statusLastUpdatedDate;
	}
	public void setStatusLastUpdatedDate(Date statusLastUpdatedDate) {
		this.statusLastUpdatedDate = statusLastUpdatedDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSkypeId() {
		return skypeId;
	}
	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}
	public String getTotalExperience() {
		return totalExperience;
	}
	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	public String getCandidateStatus() {
		return candidateStatus;
	}
	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}
	

}
