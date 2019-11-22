package com.ojas.rpo.security.entity;

public class ReportInputDataDTO {
	
	private String reportType;
	private String input;
	private String role;
	private Long roleUserId;
	private String date;
	private Integer month;
	private Integer year;
	private Long selectedId;
	
	
	public Long getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getRoleUserId() {
		return roleUserId;
	}
	public void setRoleUserId(Long roleUserId) {
		this.roleUserId = roleUserId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}

}
