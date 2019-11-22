package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="interviewfeedback")
@javax.persistence.Entity
public class InterviewFeedback implements Entity {
	@Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;
    
    @Column
    private Long requirementId;

    @Column
    private Long candidateid;
    
    @Column
    private String candidateiname;
    @Column
    private String candidateemail;
     @Column
    private String typeofprocess;
    @Column
    private String companyname;
    @Column
    private String requirementType;
    @Column
    private String nameOfRound;
    @Column
    private String interviewStatus;
    @Column
    private String clientFeedback;
    @Column
    private String internalFeedback;
    @Column
    private String recommendedNextRound;
    @Column
    private String recommendedProcess;
    
    private Date lastUpdatedDate;
    
    private Long userId;
    
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCandidateemail() {
		return candidateemail;
	}
  
	public void setCandidateemail(String candidateemail) {
		this.candidateemail = candidateemail;
	}

	public InterviewFeedback()
    {
        this.date = new Date();
    }
    
    @JsonView(JsonViews.Admin.class)
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonView(JsonViews.User.class)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@JsonView(JsonViews.User.class)
	public Long getCandidateid() {
		return candidateid;
	}
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	@JsonView(JsonViews.User.class)
	public String getCandidateiname() {
		return candidateiname;
	}
	public void setCandidateiname(String candidateiname) {
		this.candidateiname = candidateiname;
	}
	
	@JsonView(JsonViews.User.class)
	public String getTypeofprocess() {
		return typeofprocess;
	}
	public void setTypeofprocess(String typeofprocess) {
		this.typeofprocess = typeofprocess;
	}
	
	@JsonView(JsonViews.User.class)
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	@JsonView(JsonViews.User.class)
	public String getRequirementType() {
		return requirementType;
	}
	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}
	
	@JsonView(JsonViews.User.class)
	public String getNameOfRound() {
		return nameOfRound;
	}
	public void setNameOfRound(String nameOfRound) {
		this.nameOfRound = nameOfRound;
	}
	
	@JsonView(JsonViews.User.class)
	public String getInterviewStatus() {
		return interviewStatus;
	}
	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}
	
	@JsonView(JsonViews.User.class)
	public String getClientFeedback() {
		return clientFeedback;
	}
	public void setClientFeedback(String clientFeedback) {
		this.clientFeedback = clientFeedback;
	}
	@JsonView(JsonViews.User.class)
	public String getInternalFeedback() {
		return internalFeedback;
	}
	public void setInternalFeedback(String internalFeedback) {
		this.internalFeedback = internalFeedback;
	}
	
	@JsonView(JsonViews.User.class)
	public String getRecommendedNextRound() {
		return recommendedNextRound;
	}

	public void setRecommendedNextRound(String recommendedNextRound) {
		this.recommendedNextRound = recommendedNextRound;
	}

	@JsonView(JsonViews.User.class)
	public String getRecommendedProcess() {
		return recommendedProcess;
	}


	public void setRecommendedProcess(String recommendedProcess) {
		this.recommendedProcess = recommendedProcess;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	

}
