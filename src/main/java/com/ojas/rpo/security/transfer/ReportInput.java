package com.ojas.rpo.security.transfer;

import java.util.Date;

public class ReportInput {
	
	private String year;
	private String month;
	private String typeOfReport;
	Date  dateString;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTypeOfReport() {
		return typeOfReport;
	}
	public void setTypeOfReport(String typeOfReport) {
		this.typeOfReport = typeOfReport;
	}
	public Date getDateString() {
		return dateString;
	}
	public void setDateString(Date dateString) {
		this.dateString = dateString;
	}

}
