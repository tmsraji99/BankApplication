package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="customertype")
@javax.persistence.Entity
public class CustomerType implements com.ojas.rpo.security.entity.Entity {
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;

	@Column(unique = true, nullable = false)
	private String customerType;
	@Column
	private String dropdown1;

	@Column
	private Long amount;
	@Column
	private String dropdown2;

	@Column
	private Float commissionRate;

	public CustomerType() {
		this.date = new Date();
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
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@JsonView(JsonViews.User.class)
	public String getDropdown1() {
		return dropdown1;
	}

	public void setDropdown1(String dropdown1) {
		this.dropdown1 = dropdown1;
	}

	@JsonView(JsonViews.User.class)
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@JsonView(JsonViews.User.class)
	public String getDropdown2() {
		return dropdown2;
	}

	public void setDropdown2(String dropdown2) {
		this.dropdown2 = dropdown2;
	}

	@JsonView(JsonViews.User.class)
	public Float getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Float commissionRate) {
		this.commissionRate = commissionRate;
	}

	@Override
	public String toString() {
		return "CustomerType [id=" + id + ", date=" + date + ", customerType=" + customerType + ", amount=" + amount
				+ ", commissionRate=" + commissionRate + "]";
	}

}
