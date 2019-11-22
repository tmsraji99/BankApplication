package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "candidatemapping")
@javax.persistence.Entity
public class CandidateMapping implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7890357955827364622L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private BdmReq bdmReq;

	@ManyToOne
	private Candidate candidate;

	public String getOffRejectedReasion() {
		return offRejectedReasion;
	}

	public void setOffRejectedReasion(String offRejectedReasion) {
		this.offRejectedReasion = offRejectedReasion;
	}

	public String getOffereLetter() {
		return offereLetter;
	}

	public void setOffereLetter(String offereLetter) {
		this.offereLetter = offereLetter;
	}

	public String getCandidateInterviewScheduleStatus() {
		return candidateInterviewScheduleStatus;
	}

	public void setCandidateInterviewScheduleStatus(String candidateInterviewScheduleStatus) {
		this.candidateInterviewScheduleStatus = candidateInterviewScheduleStatus;
	}

	public String getSlot1Time() {
		return slot1Time;
	}

	public void setSlot1Time(String slot1Time) {
		this.slot1Time = slot1Time;
	}

	public Date getSlot1Date() {
		return slot1Date;
	}

	public void setSlot1Date(Date slot1Date) {
		this.slot1Date = slot1Date;
	}

	public String getSlot2Time() {
		return slot2Time;
	}

	public void setSlot2Time(String slot2Time) {
		this.slot2Time = slot2Time;
	}

	public Date getSlot2Date() {
		return slot2Date;
	}

	public void setSlot2Date(Date slot2Date) {
		this.slot2Date = slot2Date;
	}

	public String getSlot3Time() {
		return slot3Time;
	}

	public void setSlot3Time(String slot3Time) {
		this.slot3Time = slot3Time;
	}

	public Date getSlot3Date() {
		return slot3Date;
	}

	public void setSlot3Date(Date slot3Date) {
		this.slot3Date = slot3Date;
	}

	public Date getStatusLastUpdatedDate() {
		return statusLastUpdatedDate;
	}

	public void setStatusLastUpdatedDate(Date statusLastUpdatedDate) {
		this.statusLastUpdatedDate = statusLastUpdatedDate;
	}

	private String status;

	private String reason;

	private String candidateStatus;

	@Column
	private String offeredAndReliving;

	@Column
	private String offereStatus;

	@Column
	private String offRejectedReason;

	@Column
	private String offerLetter;

	@Column
	private String offeredCtc;

	@Column
	private String onBoardedStatus;

	@Column
	private String onBoardedDate;

	@Column
	private String OffRejectedReasion;

	@Column
	private Date absconded_date;

	private Date lastUpdatedDate;

	private Date submissionDate;

	@ManyToOne
	public User mappedUser;

	@Column
	private Date doj;
	@Column
	private String offRejectedReasion;

	@Column
	private String offereLetter;
	@Column
	private String candidateInterviewScheduleStatus;
	@Column
	private String slot1Time;
	@Column
	private Date slot1Date;
	@Column
	private String slot2Time;
	@Column
	private Date slot2Date;
	@Column
	private String slot3Time;
	@Column
	private Date slot3Date;

	@Column
	private Date statusLastUpdatedDate;
	@Column(columnDefinition = "integer default 0")
	Integer incetiveProcessed;
	@Column(columnDefinition = "integer default 0")
	Integer isAmountDebited;

	public Integer getIncetiveProcessed() {
		return incetiveProcessed;
	}

	public void setIncetiveProcessed(Integer incetiveProcessed) {
		this.incetiveProcessed = incetiveProcessed;
	}

	public Integer getIsAmountDebited() {
		return isAmountDebited;
	}

	public void setIsAmountDebited(Integer isAmountDebited) {
		this.isAmountDebited = isAmountDebited;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getOfferedAndReliving() {
		return offeredAndReliving;
	}

	public void setOfferedAndReliving(String offeredAndReliving) {
		this.offeredAndReliving = offeredAndReliving;
	}

	public String getOffereStatus() {
		return offereStatus;
	}

	public void setOffereStatus(String offereStatus) {
		this.offereStatus = offereStatus;
	}

	public String getOffRejectedReason() {
		return offRejectedReason;
	}

	public void setOffRejectedReason(String offRejectedReason) {
		this.offRejectedReason = offRejectedReason;
	}

	public String getOfferLetter() {
		return offerLetter;
	}

	public void setOfferLetter(String offerLetter) {
		this.offerLetter = offerLetter;
	}

	public String getOfferedCtc() {
		return offeredCtc;
	}

	public void setOfferedCtc(String offeredCtc) {
		this.offeredCtc = offeredCtc;
	}

	public String getOnBoardedStatus() {
		return onBoardedStatus;
	}

	public void setOnBoardedStatus(String onBoardedStatus) {
		this.onBoardedStatus = onBoardedStatus;
	}

	public String getOnBoardedDate() {
		return onBoardedDate;
	}

	public void setOnBoardedDate(String onBoardedDate) {
		this.onBoardedDate = onBoardedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Date getAbsconded_date() {
		return absconded_date;
	}

	public void setAbsconded_date(Date absconded_date) {
		this.absconded_date = absconded_date;
	}

	public User getMappedUser() {
		return mappedUser;
	}

	public void setMappedUser(User mappedUser) {
		this.mappedUser = mappedUser;
	}
}
