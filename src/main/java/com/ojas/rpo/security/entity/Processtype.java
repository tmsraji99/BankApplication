package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;

import com.ojas.rpo.security.JsonViews;
@Table(name="processtype")
@javax.persistence.Entity
public class Processtype implements Entity {

	@Id
	@GeneratedValue
	private Long id;
	@Column
    private Date date;

    @Column
    private Long candidateid;
    
    @Column
    private String candidateiname;
    @Column
    private String candidateEmail;
     @Column
    private String typeofprocess;
    @Column
    private String companyname;
    @Column
    private String address;
    @Column
    private Long pincode;
    @Column
    private String district;
    @Column
    private String state;
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private String typeofinterview;
    @Column
    private String contactperson;
    @Column
    private Long mobilenumber;
    @Column
    private Date interviewdate;
    @Column
    private String time;
    
    private Long requirementId;
    
    private Long userId;
    
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	@Column
    private String webxId;
    
    
   
    public String getWebxId() {
		return webxId;
	}

	public void setWebxId(String webxId) {
		this.webxId = webxId;
	}

	public Processtype()
    {
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
	public Long getCandidateid() {
		return candidateid;
	}
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	@JsonView(JsonViews.User.class)
	public String getCandidateiname() {
		return candidateiname;
	}
	public void setCandidateiname(String candidateiname) {
		this.candidateiname = candidateiname;
	}
	@JsonView(JsonViews.User.class)
	public String getTypeofprocess() {
		return typeofprocess;
		
	}
	public void setTypeofprocess(String typeofprocess) {
		this.typeofprocess = typeofprocess;
	}
	@JsonView(JsonViews.User.class)
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	@JsonView(JsonViews.User.class)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonView(JsonViews.User.class)
	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}
	@JsonView(JsonViews.User.class)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@JsonView(JsonViews.User.class)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@JsonView(JsonViews.User.class)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	@JsonView(JsonViews.User.class)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
    @JsonView(JsonViews.User.class)
	public String getTypeofinterview() {
		return typeofinterview;
	}
	public void setTypeofinterview(String typeofinterview) {
		this.typeofinterview = typeofinterview;
	}
	@JsonView(JsonViews.User.class)
	public String getContactperson() {
		return contactperson;
	}
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}
	@JsonView(JsonViews.User.class)
	public Long getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(Long mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	@JsonView(JsonViews.User.class)
	public Date getInterviewdate() {
		return interviewdate;
	}
	public void setInterviewdate(Date interviewdate) {
		this.interviewdate = interviewdate;
	}
	@JsonView(JsonViews.User.class)
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	@Override
	public String toString() {
		return "Processtype [id=" + id + ", date=" + date + ", candidateid=" + candidateid + ",candidateiname=" + candidateiname + ",typeofprocess=" + typeofprocess + ",companyname=" + companyname + ",address=" + address + ",pincode=" + pincode + ",city=" + city + ",state=" + state + ",country=" + country + ",typeofinterview=" + typeofinterview + ",contactperson=" + contactperson + ",mobilenumber=" + mobilenumber + ",interviewdate=" + interviewdate + ", time=" + time + "]";
	} 
}
