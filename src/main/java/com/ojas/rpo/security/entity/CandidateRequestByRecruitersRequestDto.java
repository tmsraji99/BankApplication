package com.ojas.rpo.security.entity;

import java.util.Date;

public class CandidateRequestByRecruitersRequestDto {

	private Long id;

	private Long requestedUserId;

	private Long ownerUserId;

	private Long candidateId;

	private String candidateName;

	private String recuiterName;

	private String requestedRecuiterName;

	private String mobile;

	private String email;
	
	private String ownerName;

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getRecuiterName() {
		return recuiterName;
	}

	public void setRecuiterName(String recuiterName) {
		this.recuiterName = recuiterName;
	}

	public String getRequestedRecuiterName() {
		return requestedRecuiterName;
	}

	public void setRequestedRecuiterName(String requestedRecuiterName) {
		this.requestedRecuiterName = requestedRecuiterName;
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

	private String requestStatus;

	private Date requestedDate;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRequestedUserId() {
		return requestedUserId;
	}

	public void setRequestedUserId(Long requestedUserId) {
		this.requestedUserId = requestedUserId;
	}

	public Long getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	

}
