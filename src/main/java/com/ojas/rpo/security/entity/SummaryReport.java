package com.ojas.rpo.security.entity;

public class SummaryReport {
	private Long id;
	private Long requirementId;

	private Integer statusCount;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String nameOfTheReq;
	private String candidateStatus;

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public Integer getStatusCount() {
		return statusCount;
	}

	public void setStatusCount(Integer statusCount) {
		this.statusCount = statusCount;
	}

	public String getNameOfTheReq() {
		return nameOfTheReq;
	}

	public void setNameOfTheReq(String nameOfTheReq) {
		this.nameOfTheReq = nameOfTheReq;
	}

	public String getCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

}
