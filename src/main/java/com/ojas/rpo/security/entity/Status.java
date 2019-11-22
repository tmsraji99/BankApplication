package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="status")
@javax.persistence.Entity
public class Status implements Entity {
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
	@Column(unique = true, nullable = false)
	private String status;
	@Column
	private String roleName;
	
	private String displayName;
	
	

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Status() {
		this.date = new Date();
	}

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonView(JsonViews.Admin.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonView(JsonViews.Admin.class)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@JsonView(JsonViews.Admin.class)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Status [id=" + id + ", date=" + date + ", status=" + status + " roleName="+roleName+"]";
	}

}
