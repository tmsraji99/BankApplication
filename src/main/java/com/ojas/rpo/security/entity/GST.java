package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="GST")
@javax.persistence.Entity
public class GST implements Entity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String Gst;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGst() {
		return Gst;
	}

	public void setGst(String gst) {
		Gst = gst;
	}

	
	

	

}
