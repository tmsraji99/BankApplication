package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "timeslots")
@javax.persistence.Entity
public class TimeSlot implements Entity {

	@Id
	@GeneratedValue
	private Long id;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	private Long userId;

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
	private String reason;

	@ManyToOne
	private BdmReq requirement;

	@ManyToOne
	private Candidate candidate;

	@Column
	private String candidateInterviewScheduleStatus;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BdmReq getRequirement() {
		return requirement;
	}

	public void setRequirement(BdmReq requirement) {
		this.requirement = requirement;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getCandidateInterviewScheduleStatus() {
		return candidateInterviewScheduleStatus;
	}

	public void setCandidateInterviewScheduleStatus(String candidateInterviewScheduleStatus) {
		this.candidateInterviewScheduleStatus = candidateInterviewScheduleStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}

/*
 * $sql =
 * "SELECT DATE(datecolumn) as mydate, TIME(datecolumn) as mytime FROM `tablename`"
 * ;
 */