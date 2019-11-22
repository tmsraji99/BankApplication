package com.ojas.rpo.security.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="interviewtype")
@javax.persistence.Entity
public class InterviewType implements Entity {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
	
	@Column(unique = true, nullable = false)
	private String modeofInterview;

/*    public InterviewType()
    {
        this.date = new Date();
    }*/

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	  @JsonView(JsonViews.User.class)
	    public Date getDate()
	    {
	        return this.date;
	    }

	    public void setDate(Date date)
	    {
	        this.date = date;
	    }

	@JsonView(JsonViews.User.class)
	public String getModeofInterview() {
		return modeofInterview;
	}

	public void setModeofInterview(String modeofInterview) {
		this.modeofInterview = modeofInterview;
	}

	@Override
	public String toString() {
		return "InterviewType [id=" + id + ", date=" + date + ", modeofInterview=" + modeofInterview + "]";
	}

}
