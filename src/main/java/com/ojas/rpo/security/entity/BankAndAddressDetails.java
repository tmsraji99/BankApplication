package com.ojas.rpo.security.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Table(name="bankandaddressdetails")
@javax.persistence.Entity
public class BankAndAddressDetails implements Entity {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String cheque;
	@Column
	private String accountNumber;
	@Column
	private String bankName;
	@Column
	private String iFSC;
	@Column
	private String address;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCheque() {
		return cheque;
	}
	public void setCheque(String cheque) {
		this.cheque = cheque;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getiFSC() {
		return iFSC;
	}
	public void setiFSC(String iFSC) {
		this.iFSC = iFSC;
	}
}
