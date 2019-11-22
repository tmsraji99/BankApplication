package com.ojas.rpo.security.entity;

import java.util.Date;

public class CandidateData {
	
		

	private String candidateid;
	
	
	private String bdmReqId;
	

	private String clientName; 
	
	
	private String candidateName;
	
	
	private String candidateSubmitionDate;
	
	
	private String candidateStatus;
	
	private byte[] file;
	
	private String nameOfRound;
	
	private String nameOfTheReq;
	
	private String nameOftheRecruiter;
	
	private String offerStatus;
	
	private Long userId;
	
	private Date lastUpdatedDate;
	
	private String ctc;
	
	private String typeOfHiring;
	
	
	public String getCtc() {
		return ctc;
	}



	public void setCtc(String ctc) {
		this.ctc = ctc;
	}



	public String getTypeOfHiring() {
		return typeOfHiring;
	}



	public void setTypeOfHiring(String typeOfHiring) {
		this.typeOfHiring = typeOfHiring;
	}



	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}



	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public String getOfferStatus() {
		return offerStatus;
	}



	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}



	public String getNameOftheRecruiter() {
		return nameOftheRecruiter;
	}



	public void setNameOftheRecruiter(String nameOftheRecruiter) {
		this.nameOftheRecruiter = nameOftheRecruiter;
	}



	public String getNameOfTheReq() {
		return nameOfTheReq;
	}



	public void setNameOfTheReq(String nameOfTheReq) {
		this.nameOfTheReq = nameOfTheReq;
	}



	public String getNameOfRound() {
		return nameOfRound;
	}



	public void setNameOfRound(String nameOfRound) {
		this.nameOfRound = nameOfRound;
	}

	public CandidateData() {
		
	}

	public String getCandidateid() {
		return candidateid;
	}


	public void setCandidateid(String candidateid) {
		this.candidateid = candidateid;
	}


	public String getBdmReqId() {
		return bdmReqId;
	}


	public void setBdmReqId(String bdmReqId) {
		this.bdmReqId = bdmReqId;
	}


	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public String getCandidateSubmitionDate() {
		return candidateSubmitionDate;
	}


	public void setCandidateSubmitionDate(String candidateSubmitionDate) {
		this.candidateSubmitionDate = candidateSubmitionDate;
	}


	public String getCandidateStatus() {
		return candidateStatus;
	}


	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}



	public String getCandidateName() {
		return candidateName;
	}



	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}



	public byte[] getFile() {
		return file;
	}



	public void setFile(byte[] file) {
		this.file = file;
	}

	
}
