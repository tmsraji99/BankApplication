package com.ojas.es.entity;

import java.sql.Date;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Table(name="certificate")
@javax.persistence.Entity
public class Certificate implements Entity {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
    @Column(unique = true,nullable = false)
	private String certificationName;
	
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCertificationName() {
		return certificationName;
	}
	public void setCertificationName(String certificationName) {
		this.certificationName = certificationName;
	}
	
	
}
