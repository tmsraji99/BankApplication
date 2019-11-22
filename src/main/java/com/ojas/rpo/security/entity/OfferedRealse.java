package com.ojas.rpo.security.entity;

import java.sql.Date;

import javax.persistence.Column;

public class OfferedRealse {
	
	private Date doj;
	private String offereLetter;
	
	private String offereStatus;
	
	private String offRejectedReasion;
	
	public String getOffereLetter() {
		return offereLetter;
	}
	public void setOffereLetter(String offereLetter) {
		this.offereLetter = offereLetter;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	public String getOffereStatus() {
		return offereStatus;
	}
	public void setOffereStatus(String offereStatus) {
		this.offereStatus = offereStatus;
	}
	public String getOffRejectedReasion() {
		return offRejectedReasion;
	}
	public void setOffRejectedReasion(String offRejectedReasion) {
		this.offRejectedReasion = offRejectedReasion;
	}
	
	
}
