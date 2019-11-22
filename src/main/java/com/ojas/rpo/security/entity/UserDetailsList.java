package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="userdetailslist")
@javax.persistence.Entity
public class UserDetailsList implements Entity {
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private Integer mobileNo;
	@Column
	private String email;
	@Column
	private String role;
	@Column
	private String status;

	public UserDetailsList() {
		this.date = new Date();
		this.status = "Active";
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
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonView(JsonViews.User.class)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonView(JsonViews.User.class)
	public Integer getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Integer mobileNo) {
		this.mobileNo = mobileNo;
	}

	@JsonView(JsonViews.User.class)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonView(JsonViews.User.class)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@JsonView(JsonViews.User.class)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("UserDetailsList[%d, %s]", this.id, this.date, this.firstName, this.lastName,
				this.mobileNo, this.email, this.role);
	}

}
