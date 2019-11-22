package com.ojas.rpo.security.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="incentiverole")
@javax.persistence.Entity
public class IncentiveRole implements Entity{

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
	@Column
	private String role;
	
	 public IncentiveRole() {
		this.date= new Date();
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
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@JsonView(JsonViews.User.class)
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
