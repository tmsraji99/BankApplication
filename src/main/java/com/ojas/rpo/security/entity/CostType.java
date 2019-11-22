package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="costtype")
@javax.persistence.Entity
public class CostType implements Entity {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Date date;

	@Column(unique = true, nullable = false)
	private String costtype;

	public CostType() {
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
	public String getCostType() {
		return costtype;
	}

	public void setCosttype(String costtype) {
		this.costtype = costtype;
	}

	@Override
	public String toString() {
		return "Costtype [id=" + id + ", date=" + date + ", costtype=" + costtype + "]";
	}

}
