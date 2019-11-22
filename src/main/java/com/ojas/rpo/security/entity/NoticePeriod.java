package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="noticeperiod")
@javax.persistence.Entity
public class NoticePeriod implements Entity {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
    @Column(unique = true,nullable = false)
	private Long noticePeriod;

	public NoticePeriod() {
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
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonView(JsonViews.User.class)
	public Long getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(Long noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	@Override
	public String toString() {
		return "NoticePeriod [id=" + id + ", date=" + date + ", noticePeriod=" + noticePeriod + "]";
	}

}
