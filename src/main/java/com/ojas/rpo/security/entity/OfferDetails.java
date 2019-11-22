package com.ojas.rpo.security.entity;

import java.util.Date;

public class OfferDetails {

	private String firstName;
	private String lastName;
	private Date doj;

	private String doj1;
	private String designation;
	private String hrFirstName;
	private String expectedPackage;
    private String hrLastName;
	
    
    
    
	public String getDoj1() {
		return doj1;
	}

	public void setDoj1(String doj1) {
		this.doj1 = doj1;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}



	public String getHrFirstName() {
		return hrFirstName;
	}

	public void setHrFirstName(String hrFirstName) {
		this.hrFirstName = hrFirstName;
	}

	public String getHrLastName() {
		return hrLastName;
	}

	public void setHrLastName(String hrLastName) {
		this.hrLastName = hrLastName;
	}

	public String getExpectedPackage() {
		return expectedPackage;
	}

	public void setExpectedPackage(String expectedPackage) {
		this.expectedPackage = expectedPackage;
	}

}
