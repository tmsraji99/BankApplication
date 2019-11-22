package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;

@Table(name = "addcontact")
@javax.persistence.Entity
public class AddContact implements Entity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;

	@Column
	private String contact_Name;
	@Column
	private String phone;
	@Column
	private String mobile;
	@Column
	private String email;

	@Column
	private String dob;
	@Column
	private String anniversary;
	@Column
	private String location;
	@ManyToOne
	private Client client;
	@Column
	private String status;

	private Date lastUpdatedDate;

	private Long secondaryContact_id;

	private Long accountManger_id;

	private Long lead_id;

	
	private Long primaryContact_id;

	public Long getLead_id() {
		return lead_id;
	}

	public Long getPrimaryContact_id() {
		return primaryContact_id;
	}

	public void setPrimaryContact_id(Long primaryContact_id) {
		this.primaryContact_id = primaryContact_id;
	}

	public void setLead_id(Long lead_id) {
		this.lead_id = lead_id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Long getSecondaryContact_id() {
		return secondaryContact_id;
	}

	public void setSecondaryContact_id(Long secondaryContact_id) {
		this.secondaryContact_id = secondaryContact_id;
	}

	public Long getAccountManger_id() {
		return accountManger_id;
	}

	public void setAccountManger_id(Long accountManger_id) {
		this.accountManger_id = accountManger_id;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAnniversary() {
		return anniversary;
	}

	public void setAnniversary(String anniversary) {
		this.anniversary = anniversary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public AddContact() {
		this.date = new Date();
	}

	@JsonView(JsonViews.Admin.class)
	public Long getid() {
		return id;
	}

	public void setAddContact_id(Long id) {
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
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonView(JsonViews.User.class)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonView(JsonViews.User.class)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonView(JsonViews.User.class)
	public String getContact_Name() {
		return contact_Name;
	}

	public void setContact_Name(String contact_Name) {
		this.contact_Name = contact_Name;
	}

	@Override
	public String toString() {
		return "AddContact [id=" + id + ", date=" + date + ", contact_Name=" + contact_Name + ", phone=" + phone
				+ ", mobile=" + mobile + ", email=" + email + "]";
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

}
