package com.ojas.rpo.security.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.annotations.GenericGenerator;

import com.ojas.rpo.security.JsonViews;

/**
 * JPA Annotated Pojo that represents a blog post.
 *
 */
@Table(name = "bdmreq")
@javax.persistence.Entity
public class BdmReq implements Entity {
	@Id
	@GenericGenerator(name = "req_id", strategy = "com.ojas.rpo.security.util.RequirementIdGenerator")
	@GeneratedValue(generator = "req_id")
	private Long id;

	private String startDate;
	private String endDate;

	@Lob
	private String jobDes;

	private String amountType;

	@ManyToOne
	private User createdBy;

	@ManyToOne
	private User modifiedBy;

	@Column
	private Date lastUpdatedDate;

	@Column
	private Date date;
	@Column
	private String requirementStatus;

	@Column
	private String minBudgetRate;
	@Column
	private String maxBudgetRate;

	@Column
	private String minBudget;
	@Column
	private String maxBudget;

	@Column
	private String budgetrate;

	@Column
	private Date requirementStartdate;

	@Column
	private String tax_amount_type;

	@Column
	private Date requirementEndDate;

	@Column
	private String nameOfRequirement;

	/*
	 * @Column private String jobLocation;
	 */

	@Column
	private String noticePeriod;

	@Column
	private String typeOfHiring;

	@Column
	private String salaryBand;

	@Lob
	@Column
	private String requirementDescription;

	@Column
	private String permanent;

	@Column
	private String famount;

	@Column
	private String requirementType;

	@Column
	private String newtype;

	@Column
	private String netPeriod;

	@Column
	private String gross_Amount;

	@Column
	private String totalExperience;

	@Column
	private String relavantExperience;

	@Column
	private String billRate;

	@Column
	private String tax;

	@Column
	private String minimumContract;

	@Column
	private String numberOfPositions;

	@Column
	private String status;

	@Column
	private String numberOfRounds;

	@Column
	private String conversionFee;

	/*
	 * @ManyToMany
	 * 
	 * @JoinTable(name = "reqlocationMapping", joinColumns = @JoinColumn(name =
	 * "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns
	 * = @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")) private
	 * List<Location> location = new ArrayList<Location>();
	 */

	@ManyToMany
	@JoinTable(name = "reqLocationsMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID"))
	private List<Location> locations = new ArrayList<Location>();

	@ManyToMany
	@JoinTable(name = "reqskillMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "SKILL_ID", referencedColumnName = "ID"))
	private List<Skill> skills = new ArrayList<Skill>();

	@ManyToMany
	@JoinTable(name = "reqdesignationMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "DESIGNATION_ID", referencedColumnName = "ID"))
	private List<Designation> designations = new ArrayList<Designation>();

	@ManyToMany
	@JoinTable(name = "reqqualificationMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "QULIFICATION_ID", referencedColumnName = "ID"))
	private List<AddQualification> qualifications = new ArrayList<AddQualification>();

	@ManyToMany
	@JoinTable(name = "reqcertificatesMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "CERTIFICATE_ID", referencedColumnName = "ID"))
	private List<Certificate> certificates = new ArrayList<Certificate>();

	@ManyToMany
	@JoinTable(name = "recuriterMap", joinColumns = @JoinColumn(name = "Assign_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "recuriter_ID", referencedColumnName = "ID"))
	private List<User> recrutier = new ArrayList<User>();

	public List<User> getRecrutier() {
		return recrutier;
	}

	public void setRecrutier(List<User> recrutier) {
		this.recrutier = recrutier;
	}

	@ManyToOne
	private Client client;

	@ManyToOne
	private User user;

	@ManyToOne
	private AddContact addContact;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRequirementStatus() {
		return requirementStatus;
	}

	public void setRequirementStatus(String requirementStatus) {
		this.requirementStatus = requirementStatus;
	}

	public String getMinBudgetRate() {
		return minBudgetRate;
	}

	public void setMinBudgetRate(String minBudgetRate) {
		this.minBudgetRate = minBudgetRate;
	}

	public String getMaxBudgetRate() {
		return maxBudgetRate;
	}

	public void setMaxBudgetRate(String maxBudgetRate) {
		this.maxBudgetRate = maxBudgetRate;
	}

	public String getBudgetrate() {
		return budgetrate;
	}

	public void setBudgetrate(String budgetrate) {
		this.budgetrate = budgetrate;
	}

	public String getTax_amount_type() {
		return tax_amount_type;
	}

	public void setTax_amount_type(String tax_amount_type) {
		this.tax_amount_type = tax_amount_type;
	}

	public Date getRequirementEndDate() {
		return requirementEndDate;
	}

	public void setRequirementEndDate(Date requirementEndDate) {
		this.requirementEndDate = requirementEndDate;
	}

	public String getNewtype() {
		return newtype;
	}

	public void setNewtype(String newtype) {
		this.newtype = newtype;
	}

	public String getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}

	public Date getRequirementStartdate() {
		return requirementStartdate;
	}

	public void setRequirementStartdate(Date requirementStartdate) {
		this.requirementStartdate = requirementStartdate;
	}

	public String getPermanent() {
		return permanent;
	}

	public void setPermanent(String permanent) {
		this.permanent = permanent;
	}

	public String getMinimumContract() {
		return minimumContract;
	}

	public void setMinimumContract(String minimumContract) {
		this.minimumContract = minimumContract;
	}

	public String getBillRate() {
		return billRate;
	}

	public void setBillRate(String billRate) {
		this.billRate = billRate;
	}

	public String getFamount() {
		return famount;
	}

	public void setFamount(String famount) {
		this.famount = famount;
	}

	public String getNetPeriod() {
		return netPeriod;
	}

	public void setNetPeriod(String netPeriod) {
		this.netPeriod = netPeriod;
	}

	public String getGross_Amount() {
		return gross_Amount;
	}

	public void setGross_Amount(String gross_Amount) {
		this.gross_Amount = gross_Amount;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getNameOfRequirement() {
		return nameOfRequirement;
	}

	public void setNameOfRequirement(String nameOfRequirement) {
		this.nameOfRequirement = nameOfRequirement;
	}

	/*
	 * public String getJobLocation() { return jobLocation; }
	 * 
	 * public void setJobLocation(String jobLocation) { this.jobLocation =
	 * jobLocation; }
	 */

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getTypeOfHiring() {
		return typeOfHiring;
	}

	public void setTypeOfHiring(String typeOfHiring) {
		this.typeOfHiring = typeOfHiring;
	}

	public String getSalaryBand() {
		return salaryBand;
	}

	public void setSalaryBand(String salaryBand) {
		this.salaryBand = salaryBand;
	}

	public String getRequirementDescription() {
		return requirementDescription;
	}

	public void setRequirementDescription(String requirementDescription) {
		this.requirementDescription = requirementDescription;
	}

	public String getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	public String getRelavantExperience() {
		return relavantExperience;
	}

	public void setRelavantExperience(String relavantExperience) {
		this.relavantExperience = relavantExperience;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNumberOfPositions() {
		return numberOfPositions;
	}

	public void setNumberOfPositions(String numberOfPositions) {
		this.numberOfPositions = numberOfPositions;
	}

	public String getNumberOfRounds() {
		return numberOfRounds;
	}

	public void setNumberOfRounds(String numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}

	public String getConversionFee() {
		return conversionFee;
	}

	public void setConversionFee(String conversionFee) {
		this.conversionFee = conversionFee;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AddContact getAddContact() {
		return addContact;
	}

	public void setAddContact(AddContact addContact) {
		this.addContact = addContact;
	}

	public List<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}

	public List<AddQualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<AddQualification> qualifications) {
		this.qualifications = qualifications;
	}

	public List<Designation> getDesignations() {
		return designations;
	}

	public void setDesignations(List<Designation> designations) {
		this.designations = designations;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public BdmReq() {
		this.date = new Date();
		this.status = "Open";
		this.requirementStartdate = new Date();
	}

	@JsonView(JsonViews.Admin.class)
	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return this.id;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getJobDes() {
		return jobDes;
	}

	public void setJobDes(String jobDes) {
		this.jobDes = jobDes;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public String getMinBudget() {
		return minBudget;
	}

	public void setMinBudget(String minBudget) {
		this.minBudget = minBudget;
	}

	public String getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(String maxBudget) {
		this.maxBudget = maxBudget;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

}
