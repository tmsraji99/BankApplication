package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Table(name="addressdetails")
@javax.persistence.Entity
public class AddressDetails implements Entity {
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date date;
	@Column
	private String addressLane1;
	@Column
	private String addressLane2;
	@Column
	private String city;
	@Column
	private String country;
	@Column
	private String state;
	@Column
	private long pincode;
	@ManyToOne
	private TypeOfAddress typeOfAddress;
	@Column
	private String gst;
	@Column
	private String gstpercentage;
	@Column
	private Boolean isSez;
	@Column
	private Long cid;

	
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public AddressDetails() {
		this.date = new Date();
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getGstpercentage() {
		return gstpercentage;
	}

	public void setGstpercentage(String gstpercentage) {
		this.gstpercentage = gstpercentage;
	}

	public Boolean getIsSez() {
		return isSez;
	}

	public void setIsSez(Boolean isSez) {
		this.isSez = isSez;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAddressLane1() {
		return addressLane1;
	}

	public void setAddressLane1(String addressLane1) {
		this.addressLane1 = addressLane1;
	}

	public String getAddressLane2() {
		return addressLane2;
	}

	public void setAddressLane2(String addressLane2) {
		this.addressLane2 = addressLane2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getPincode() {
		return pincode;
	}

	public void setPincode(long pincode) {
		this.pincode = pincode;
	}

	public TypeOfAddress getTypeOfAddress() {
		return typeOfAddress;
	}

	public void setTypeOfAddress(TypeOfAddress typeOfAddress) {
		this.typeOfAddress = typeOfAddress;
	}

	

}
