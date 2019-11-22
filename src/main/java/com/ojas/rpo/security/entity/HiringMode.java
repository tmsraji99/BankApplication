package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="hiringmode")
@javax.persistence.Entity
public class HiringMode implements Entity {
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
    @Column(unique = true,nullable = false)
	private String modeOfHiring;
	@Column
	private String status;

	public HiringMode() {
		this.date = new Date();
		this.status="active";
	}

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return id;
	}
	@JsonView(JsonViews.User.class)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	public String getModeOfHiring() {
		return modeOfHiring;
	}
	public void setModeOfHiring(String modeOfHiring) {
		this.modeOfHiring = modeOfHiring;
	}

}
