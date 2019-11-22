package com.ojas.rpo.security.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.annotations.GenericGenerator;

import com.ojas.rpo.security.JsonViews;

@Table(name = "client")
@javax.persistence.Entity
public class Client implements Entity {
	@Id
    @GenericGenerator(name = "client_id", strategy = "com.ojas.rpo.security.util.ClientIdGenerator")
    @GeneratedValue(generator="client_id")
	private Long id;
	@Column(unique = true, nullable = false)
	private String clientName;
	@Column
	private Date date;
	private String date1;
	@Column
	private Long phone;
	@Column
	private String email;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private String leavesAllowed;
	@Column
	private String customershortname;
	
	@Column 
	private Long contactId;
	
	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	@Column
	private String spocName;

	@ManyToOne
	private BillingModel billingModel;

	@Column
	private String tdspercentage;
	
	
	
	private Date lastUpdatedDate; 
	
	
	

	public String getCustomershortname() {
		return customershortname;
	}

	public void setCustomershortname(String customershortname) {
		this.customershortname = customershortname;
	}

	

	@ManyToOne
	private PaymentTerms paymentTerms ;

	@ManyToMany
	@JoinTable(name = "servicesMap", joinColumns = @JoinColumn(name = "client_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "service_ID", referencedColumnName = "ID"))
	private List<Services> services ;



	

	@Override
	public String toString() {
		return "Client [id=" + id + ", clientName=" + clientName + ", date=" + date + ", date1=" + date1 + ", phone="
				+ phone + ", email=" + email + ", startDate=" + startDate + ", endDate=" + endDate + ", leavesAllowed="
				+ leavesAllowed + ", customershortname=" + customershortname + ", billingModel=" + billingModel
				+ ", tdspercentage=" + tdspercentage + 
				 ", paymentTerms=" + paymentTerms + ", services=" + services + ", addressDetails="
				+ addressDetails + ", customerType=" + customerType + "]";
	}
	
	

	public String getLeavesAllowed() {
		return leavesAllowed;
	}

	public void setLeavesAllowed(String leavesAllowed) {
		this.leavesAllowed = leavesAllowed;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cid", referencedColumnName = "id")
	private List<AddressDetails> addressDetails = new ArrayList<AddressDetails>();

	public List<AddressDetails> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(List<AddressDetails> addressDetails) {
		this.addressDetails = addressDetails;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	@ManyToOne
	private CustomerType customerType;

	
	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	@JsonView(JsonViews.User.class)

	public void setDate(Date date) {
		this.date = date;
	}

	public Client() {
		this.date = new Date();
	}

	@JsonView(JsonViews.User.class)
	public Date getDate() {
		return this.date;
	}

	@JsonView(JsonViews.User.class)
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	
	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BillingModel getBillingModel() {
		return billingModel;
	}

	public void setBillingModel(BillingModel billingModel) {
		this.billingModel = billingModel;
	}

	public PaymentTerms getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(PaymentTerms paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getTdspercentage() {
		return tdspercentage;
	}

	public void setTdspercentage(String tdspercentage) {
		this.tdspercentage = tdspercentage;
	}

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}

	public String getSpocName() {
		return spocName;
	}

	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
}
