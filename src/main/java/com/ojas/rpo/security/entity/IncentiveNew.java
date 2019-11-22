package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;

@Table(name = "incentivenew")
@javax.persistence.Entity
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "incentiveUnProceedStepUp", procedureName = "incentiveUnProceedStepUp", parameters = {
				@StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Long.class) }

		), @NamedStoredProcedureQuery(name = "CalIncentiveForDebitStepDown", procedureName = "CalIncentiveForDebitStepDown", parameters = { @StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Long.class) }), @NamedStoredProcedureQuery(name = "calIncentives", procedureName = "calIncentives", parameters = { @StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Long.class) }), @NamedStoredProcedureQuery(name = "CalIncentiveDebit", procedureName = "CalIncentiveDebit", parameters = { @StoredProcedureParameter(name = "userId", mode = ParameterMode.IN, type = Long.class) }) })
public class IncentiveNew implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
	@Column
	private Long recId;
	@Column
	private Long canId;
	@Column
	private Double cr_Amount;
	@Column
	private Double dr_Amount;

	@JsonView(JsonViews.Admin.class)
	public Long getId() {

		return null;
	}

	@JsonView(JsonViews.Admin.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public Long getCanId() {
		return canId;
	}

	public void setCanId(Long canId) {
		this.canId = canId;
	}

	public Double getCr_Amount() {
		return cr_Amount;
	}

	public void setCr_Amount(Double cr_Amount) {
		this.cr_Amount = cr_Amount;
	}

	public Double getDr_Amount() {
		return dr_Amount;
	}

	public void setDr_Amount(Double dr_Amount) {
		this.dr_Amount = dr_Amount;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
