package com.ojas.rpo.security.transfer;

import java.util.Collection;

public class ReportResultsTransfer {

	private Integer totalSubmissions;
	private Integer totalRejections;
	private Long userId;
	private String nameOfUser;
	private String userRole;
	private String base64String;
	private String reportType;
	private String day;
	private Integer month;
	private Integer year;
	private Collection<ReportsListTransfer> resultsList; 
	private String reportTypeValue;// Only used internally. not used from UI. 

	public Integer getTotalSubmissions() {
		return totalSubmissions;
	}

	public void setTotalSubmissions(Integer totalSubmissions) {
		this.totalSubmissions = totalSubmissions;
	}

	public Integer getTotalRejections() {
		return totalRejections;
	}

	public void setTotalRejections(Integer totalRejections) {
		this.totalRejections = totalRejections;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBase64String() {
		return base64String;
	}

	public void setBase64String(String base64String) {
		this.base64String = base64String;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public String getNameOfUser() {
		return nameOfUser;
	}

	public void setNameOfUser(String nameOfUser) {
		this.nameOfUser = nameOfUser;
	}

	public Collection<ReportsListTransfer> getResultsList() {
		return resultsList;
	}

	public void setResultsList(Collection<ReportsListTransfer> resultData) {
		this.resultsList = resultData;
	}

	public String getReportTypeValue() {
		return reportTypeValue;
	}

	public void setReportTypeValue(String reportTypeValue) {
		this.reportTypeValue = reportTypeValue;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
}

