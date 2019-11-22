package com.ojas.rpo.security.util;

public class ReportData {
	
	private Integer year;
	private String month;
	private String recuriterName;
	private String status;
	private Integer count;
	private String requirementName;
	private Long requirementId;
	
	private DataSet dataSet ;
	public Integer getYear() {
		return year;
	}
	public DataSet getDataSet() {
		return dataSet;
	}
	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getRecuriterName() {
		return recuriterName;
	}
	public void setRecuriterName(String recuriterName) {
		this.recuriterName = recuriterName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public Long getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}
}
