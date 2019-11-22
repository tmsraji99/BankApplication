package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="designation")
@javax.persistence.Entity
public class Designation implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;

    @Column(unique = true,nullable = false)
    private String designation;
    
    public Designation()
    {
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
    public Date getDate()
    {
        return this.date;
    }
    
    public void setDate(Date date)
    {
        this.date = date;
    }
    @JsonView(JsonViews.User.class)
    public String getDesignation() {
		return designation;
	}
    public void setDesignation(String designation) {
		this.designation = designation;
	}
  

	@Override
	public String toString() {
		return "Designation [id=" + id + ", date=" + date + ", designation=" + designation + "]";
	}

    
}
