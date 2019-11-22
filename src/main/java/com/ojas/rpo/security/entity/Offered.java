package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.annotations.Type;

import com.ojas.rpo.security.JsonViews;
@Table(name="offered")
@javax.persistence.Entity
public class Offered implements Entity {
	@Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;

    @Column
    private Long candidateId;
    @Column
    private Long rqcId;
    @Column
    private Long  amId;
    @Column
    private Long  bdmId;
    
    @Column
    private String candidateName;
    
    @Column
    private String companyName;
    
    @Column
    private String typeofProcess;
    
    @Column
    private String candidateCtc;
    
    @Column
	@Type(type="org.hibernate.type.StringClobType")
	private String letterData;
    
    @Column
    private String letterName;
    
    @Column
    private String offeredLetter;
    
    public Offered() {
		this.date = new Date();
	}

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonView(JsonViews.User.class)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	@JsonView(JsonViews.User.class)
	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	@JsonView(JsonViews.User.class)
	public Long getRqcId() {
		return rqcId;
	}

	public void setRqcId(Long rqcId) {
		this.rqcId = rqcId;
	}
	
	
	@JsonView(JsonViews.User.class)
	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	@JsonView(JsonViews.User.class)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@JsonView(JsonViews.User.class)
	public String getTypeofProcess() {
		return typeofProcess;
	}

	public void setTypeofProcess(String typeofProcess) {
		this.typeofProcess = typeofProcess;
	}
	@JsonView(JsonViews.User.class)
	public String getCandidateCtc() {
		return candidateCtc;
	}

	public void setCandidateCtc(String candidateCtc) {
		this.candidateCtc = candidateCtc;
	}
	@JsonView(JsonViews.User.class)
	public String getLetterData() {
		return letterData;
	}

	public void setLetterData(String letterData) {
		this.letterData = letterData;
	}
	@JsonView(JsonViews.User.class)
	public String getLetterName() {
		return letterName;
	}

	public void setLetterName(String letterName) {
		this.letterName = letterName;
	}
	@JsonView(JsonViews.User.class)
	public String getOfferedLetter() {
		return offeredLetter;
	}

	public void setOfferedLetter(String offeredLetter) {
		this.offeredLetter = offeredLetter;
	}

	public Long getAmId() {
		return amId;
	}

	public void setAmId(Long amId) {
		this.amId = amId;
	}

	public Long getBdmId() {
		return bdmId;
	}

	public void setBdmId(Long bdmId) {
		this.bdmId = bdmId;
	}

    
}
