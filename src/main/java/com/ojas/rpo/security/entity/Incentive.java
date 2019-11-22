package com.ojas.rpo.security.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="incentive")
@javax.persistence.Entity

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "incentiveUnProceedStepUp1", procedureName = "incentiveUnProceedStepUp", parameters = {
			@StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Long.class) }

	), @NamedStoredProcedureQuery(name = "CalIncentiveForDebitStepDown2", procedureName = "CalIncentiveForDebitStepDown", parameters = { @StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Long.class) }), @NamedStoredProcedureQuery(name = "calIncentives3", procedureName = "calIncentives", parameters = { @StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Long.class) }), @NamedStoredProcedureQuery(name = "CalIncentiveDebit4", procedureName = "CalIncentiveDebit", parameters = { @StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Long.class) }) })
public class Incentive implements Entity {	
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
	@Column
	private String requirementType;
	@Column
	private String resourceType;
	@Column
	private Long rate;
	@Column
	private String type;
	@Column
	private Integer duration;
	@Column
	private String typeOfDuration;
	@Column
	private Long targetAmount;
	@Column
	private String qualityCv;
	@Column
	private String revenueIncentive;
	@Column
	private Long fixedAmount;
	@Column
	private String status;
	
	public Incentive() {
		this.date= new Date();
		this.status="Active";
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
	public String getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}
	@JsonView(JsonViews.Admin.class)
	public String getResourceType() {
		return resourceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	
	public Long getRate() {
		return rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}
	
	


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
    public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	
   public String getTypeOfDuration() {
		return typeOfDuration;
	}

	public void setTypeOfDuration(String typeOfDuration) {
		this.typeOfDuration = typeOfDuration;
	}
	
	
    public Long getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Long targetAmount) {
		this.targetAmount = targetAmount;
	}


    public String getQualityCv() {
		return qualityCv;
	}

	public void setQualityCv(String qualityCv) {
		this.qualityCv = qualityCv;
	}

	public String getRevenueIncentive() {
		return revenueIncentive;
	}

	public void setRevenueIncentive(String revenueIncentive) {
		this.revenueIncentive = revenueIncentive;
	}

	public Long getFixedAmount() {
		return fixedAmount;
	}

	public void setFixedAmount(Long fixedAmount) {
		this.fixedAmount = fixedAmount;
	}



	@ManyToOne
	private IncentiveRole incentiveRole;
	

	public IncentiveRole getIncentiveRole() {
		return incentiveRole;
	}

	public void setIncentiveRole(IncentiveRole incentiveRole) {
		this.incentiveRole = incentiveRole;
	}

	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "slab_id", referencedColumnName = "id")
    private List<IncentiveSlab> slab = new ArrayList<IncentiveSlab>();



	public List<IncentiveSlab> getSlab() {
		return slab;
	}

	public void setSlab(List<IncentiveSlab> slab) {
		this.slab = slab;
	}
	
	
	
	
	
	
	
	

}
