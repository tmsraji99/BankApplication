package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="companytaxinfo")
@javax.persistence.Entity
public class CompanyTaxInfo implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String stNo;
	@Column
	private String sbcNo;
	@Column
	private String kkcNo;
	@Column
	private String panNo;
	@Column
	private String cin;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStNo() {
		return stNo;
	}
	public void setStNo(String stNo) {
		this.stNo = stNo;
	}
	public String getSbcNo() {
		return sbcNo;
	}
	public void setSbcNo(String sbcNo) {
		this.sbcNo = sbcNo;
	}
	public String getKkcNo() {
		return kkcNo;
	}
	public void setKkcNo(String kkcNo) {
		this.kkcNo = kkcNo;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}


	
	
	
	
}
