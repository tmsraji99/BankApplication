package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="incentiveslab")
@javax.persistence.Entity
public class IncentiveSlab implements Entity  {
	
	public IncentiveSlab()
	{
		this.date = new Date();
	}
	@Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;
	
	
    @Column
    private String slabType;
    
    @Column
    private Integer startFrom;
    
    @Column
    private Integer startEnd;

    @Column
    private Integer amount;

    
    @JsonView(JsonViews.User.class)
    public Integer getStartFrom() {
		return startFrom;
	}


	public void setStartFrom(Integer startFrom) {
		this.startFrom = startFrom;
	}

	@JsonView(JsonViews.User.class)
	public Integer getStartEnd() {
		return startEnd;
	}


	public void setStartEnd(Integer startEnd) {
		this.startEnd = startEnd;
	}

	@JsonView(JsonViews.User.class)
	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return id;
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
	public String getSlabType() {
		return slabType;
	}


	public void setSlabType(String slabType) {
		this.slabType = slabType;
	}


}
