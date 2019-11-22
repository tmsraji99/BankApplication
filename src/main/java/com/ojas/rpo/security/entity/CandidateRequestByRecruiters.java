package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "candidaterequestbyrecruiters")
@javax.persistence.Entity
public class CandidateRequestByRecruiters implements Entity {
	@Id
	@GeneratedValue
	private Long id;

	private Long requestedUserId;

	private Long ownerUserId;

	private Long candidateId;

	@Column
	private String requestStatus;

	@Column
	private Date requestedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
