package com.ojas.rpo.security.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="amquery")
@javax.persistence.Entity
public class Amquery implements Entity {
	@Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;
     @Column
    private String typeofprocess;
  
    @Column
    private String quryQuestion;
    @Column
    private String quryAnswer;
    @Column
    private Long candidateid;
    
   
    @Column
    private String candidateiname;
  
    @Column
    private String userEmail;
  
   
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	public String getTypeofprocess() {
		return typeofprocess;
	}

	public void setTypeofprocess(String typeofprocess) {
		this.typeofprocess = typeofprocess;
	}

	
	
	public String getQuryQuestion() {
		return quryQuestion;
	}
	public void setQuryQuestion(String quryQuestion) {
		this.quryQuestion = quryQuestion;
	}
	public String getQuryAnswer() {
		return quryAnswer;
	}
	public void setQuryAnswer(String quryAnswer) {
		this.quryAnswer = quryAnswer;
	}
	public Amquery()
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

}