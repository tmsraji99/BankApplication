package com.ojas.rpo.security.transfer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ojas.rpo.security.entity.User;


public class UserTransfer
{

	private String name;
	private String fullName;
	private String reportsTo;
	private String reportingMail;
	private Map<String, Boolean> roles;
	
    @Autowired
    private User user;

	public UserTransfer(String userName, Map<String, Boolean> roles)
	{
		this.name = userName;
		this.roles = roles;
	}
	
	public UserTransfer(String name, Map<String, Boolean> roles, User user) {
		super();
		this.name = name;
		this.roles = roles;
		this.user = user;
	}
	
	public UserTransfer() {}
	
   	public String getName()
	{
		return this.name;
	}
	public Map<String, Boolean> getRoles()
	{
		return this.roles;
	}
	public User getUser() {
		return user;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getReportsTo() {
		return reportsTo;
	}
	public void setReportsTo(String reportsTo) {
		this.reportsTo = reportsTo;
	}
	public String getReportingMail() {
		return reportingMail;
	}
	public void setReportingMail(String reportingMail) {
		this.reportingMail = reportingMail;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
 
}