package com.ojas.rpo.security.transfer;

import java.util.Date;

public class IncentivesData {
	private String recruiterName;
	private String role;
	private String candidateName;
	private Long id;
	private Date date;
	private Long recId;
	private Long canId;
	private Double cr_Amount;
	private Double dr_Amount;
	private Long reqId;
	private String requirementName;
	private String emailId;
	
	
	
	public Long getReqId() {
		return reqId;
	}
	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getRecruiterName() {
		return recruiterName;
	}
	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getRecId() {
		return recId;
	}
	public void setRecId(Long recId) {
		this.recId = recId;
	}
	public Long getCanId() {
		return canId;
	}
	public void setCanId(Long canId) {
		this.canId = canId;
	}
	public Double getCr_Amount() {
		return cr_Amount;
	}
	public void setCr_Amount(Double cr_Amount) {
		this.cr_Amount = cr_Amount;
	}
	public Double getDr_Amount() {
		return dr_Amount;
	}
	public void setDr_Amount(Double dr_Amount) {
		this.dr_Amount = dr_Amount;
	}
	
	

}
