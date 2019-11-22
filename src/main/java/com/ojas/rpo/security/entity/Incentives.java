package com.ojas.rpo.security.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;

@Table(name = "incentives")
@javax.persistence.Entity
public class Incentives implements Entity{
	@Id
	@GeneratedValue
	private Long   id;
	@Column
	private Long  userId;
	@Column
	private Long  totalNoOfIncentives;
	@Column
	private Double incentiveOfTheMonth;
	@Column
	private Double debitedIncentive;
	@Column
	private Date incentiveDate;
	@Column
	private Long candidateId;
	@Column
	private String incentiveStatus;
	
		
	public String getIncentiveStatus() {
		return incentiveStatus;
	}

	public void setIncentiveStatus(String incentiveStatus) {
		this.incentiveStatus = incentiveStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTotalNoOfIncentives() {
		return totalNoOfIncentives;
	}

	public void setTotalNoOfIncentives(Long totalNoOfIncentives) {
		this.totalNoOfIncentives = totalNoOfIncentives;
	}

	public Double getIncentiveOfTheMonth() {
		return incentiveOfTheMonth;
	}

	public void setIncentiveOfTheMonth(Double incentiveOfTheMonth) {
		this.incentiveOfTheMonth = incentiveOfTheMonth;
	}

	public Double getDebitedIncentive() {
		return debitedIncentive;
	}

	public void setDebitedIncentive(Double debitedIncentive) {
		this.debitedIncentive = debitedIncentive;
	}

	public Date getIncentiveDate() {
		return incentiveDate;
	}

	public void setIncentiveDate(Date incentiveDate) {
		this.incentiveDate = incentiveDate;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return this.id;
	}
	
	
	
	

}
