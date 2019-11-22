package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="addqualification")
@javax.persistence.Entity
public class AddQualification implements Entity {
	@Id
	@GeneratedValue
	private Long id;

	
	@Column
	private Date date;

	@Column(unique = true, nullable = false)
	private String qualificationName;
	@Column
	private String status;

	public AddQualification() {
		this.date = new Date();
		this.status = "Active";
	}
	public void setId(Long id) {
		this.id = id;
	}

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return this.id;
	}

	@JsonView(JsonViews.User.class)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonView(JsonViews.User.class)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonView(JsonViews.User.class)
	public String getqualificationName() {
		return qualificationName;
	}

	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}

	@Override
	public String toString() {
		return String.format("Location[%d, %s]", this.id, this.date, this.qualificationName);
	}
}