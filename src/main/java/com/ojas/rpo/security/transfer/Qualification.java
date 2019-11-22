package com.ojas.rpo.security.transfer;

import java.util.Date;

public class Qualification {
	private long id;
	private Date insertDate;
	private String qualificationName;
	private String qualificationStatus;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public String getQualificationName() {
		return qualificationName;
	}
	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}
	public String getQualificationStatus() {
		return qualificationStatus;
	}
	public void setQualificationStatus(String qualificationStatus) {
		this.qualificationStatus = qualificationStatus;
	}
	
}
