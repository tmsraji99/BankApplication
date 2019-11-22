package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "typeofaddress")
@javax.persistence.Entity
public class TypeOfAddress implements Entity {
	@Id
	@GeneratedValue
	private long id;
	private Date date;
	@Column(unique = true, nullable = false)
	private String typeofaddress;

	

	@Override
	public String toString() {
		return "TypeOfAddress [TypeOfAddress=" + typeofaddress + "]";
	}

	public String getTypeOfAddress() {
		return typeofaddress;
	}

	public void setTypeOfAddress(String typeOfAddress) {
		typeofaddress = typeOfAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public TypeOfAddress() {
		this.date = new Date();
	}

}
