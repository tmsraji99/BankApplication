package com.ojas.rpo.security.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CandidateStatusCounts {
	
	private String statuscount;
	
	private String bdmReqStatus;
	
	public String getBdmReqStatus() {
		return bdmReqStatus;
	}

	public void setBdmReqStatus(String bdmReqStatus) {
		this.bdmReqStatus = bdmReqStatus;
	}

	private String candidateStatus;
	
	private String recruitername;
	
	private String id;
	
	private String nameOfRequirement;
	
	private String clientname;
	
	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getStatuscount() {
		return statuscount;
	}

	public void setStatuscount(String statuscount) {
		this.statuscount = statuscount;
	}

	public String getCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	

	public String getRecruitername() {
		return recruitername;
	}

	public void setRecruitername(String recruitername) {
		this.recruitername = recruitername;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNameOfRequirement() {
		return nameOfRequirement;
	}

	public void setNameOfRequirement(String nameOfRequirement) {
		this.nameOfRequirement = nameOfRequirement;
	}



}
