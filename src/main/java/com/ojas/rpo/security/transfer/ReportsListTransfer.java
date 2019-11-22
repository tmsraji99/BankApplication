package com.ojas.rpo.security.transfer;

public class ReportsListTransfer {

	private Integer submissions;
	private Integer rejections;
	private Long userId;
	private String nameOfUser;
	private String clientName;
	private Long requirementId;
	private String requirementName;

	public Integer getSubmissions() {
		return submissions;
	}

	public void setSubmissions(Integer submissions) {
		this.submissions = submissions;
	}

	public Integer getRejections() {
		return rejections;
	}

	public void setRejections(Integer rejections) {
		this.rejections = rejections;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNameOfUser() {
		return nameOfUser;
	}

	public void setNameOfUser(String nameOfUser) {
		this.nameOfUser = nameOfUser;
	}
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
}
