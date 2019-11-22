package com.ojas.rpo.security.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Table(name="pastcandidatemappings")
@javax.persistence.Entity
public class PastAndChangedCandidateMappings implements Entity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private Long mappingId;
	
	@ManyToOne
	private BdmReq bdmReq;
	
	@ManyToOne
	private Candidate candidate;
	
	private String status;
	
	private Date changedDate;
	
	private String lastStatusOfCandidate;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BdmReq getBdmReq() {
		return bdmReq;
	}

	public void setBdmReq(BdmReq bdmReq) {
		this.bdmReq = bdmReq;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Date getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Date changedDate) {
		this.changedDate = changedDate;
	}

	public String getLastStatusOfCandidate() {
		return lastStatusOfCandidate;
	}

	public void setLastStatusOfCandidate(String lastStatusOfCandidate) {
		this.lastStatusOfCandidate = lastStatusOfCandidate;
	}

	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

}

