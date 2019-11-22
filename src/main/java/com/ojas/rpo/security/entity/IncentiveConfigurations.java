package com.ojas.rpo.security.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "incentive_configuration")
public class IncentiveConfigurations implements com.ojas.rpo.security.entity.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String role;
	private Integer target;
	private Double amount;
	private String valueType;
	private String rule;
	private Integer is_countable;
	private Double min_ctc;
	private Double max_ctc;

	public IncentiveConfigurations() {
		super();
	}

	public IncentiveConfigurations(Long id, String role, Integer target, Double amount, String valueType,
			String rule, Integer is_countable, Double min_ctc, Double max_ctc) {
		super();
		this.id = id;
		this.role = role;
		this.target = target;
		this.amount = amount;
		this.valueType = valueType;
		this.rule = rule;
		this.is_countable = is_countable;
		this.min_ctc = min_ctc;
		this.max_ctc = max_ctc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getIs_countable() {
		return is_countable;
	}

	public void setIs_countable(Integer is_countable) {
		this.is_countable = is_countable;
	}

	public Double getMin_ctc() {
		return min_ctc;
	}

	public void setMin_ctc(Double min_ctc) {
		this.min_ctc = min_ctc;
	}

	public Double getMax_ctc() {
		return max_ctc;
	}

	public void setMax_ctc(Double max_ctc) {
		this.max_ctc = max_ctc;
	}

	@Override
	public String toString() {
		return "IncentiveConfigurations [id=" + id + ", role=" + role + ", target=" + target + ", amount=" + amount
				+ ", valueType=" + valueType + ", rule=" + rule + ", is_countable=" + is_countable + ", min_ctc="
				+ min_ctc + ", max_ctc=" + max_ctc + "]";
	}

}
