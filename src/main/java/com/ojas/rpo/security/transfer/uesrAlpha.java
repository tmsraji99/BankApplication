package com.ojas.rpo.security.transfer;

public class uesrAlpha 
{
	
	String userName;
	String role;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public uesrAlpha(String userName, String role) {
		super();
		this.userName = userName;
		this.role = role;
	}
	
	

}
