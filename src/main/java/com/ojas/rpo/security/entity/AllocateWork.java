package com.ojas.rpo.security.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

public class AllocateWork {
	
	private List<User> User=new ArrayList<User>();
	private List<Client> clientList=new ArrayList<Client>();
	private List<BdmReq> bdmReqList=new ArrayList<BdmReq>();
	public List<User> getUser() {
		return User;
	}
	public void setUser(List<User> user) {
		User = user;
	}
	public List<Client> getClientList() {
		return clientList;
	}
	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}
	public List<BdmReq> getBdmReqList() {
		return bdmReqList;
	}
	public void setBdmReqList(List<BdmReq> bdmReqList) {
		this.bdmReqList = bdmReqList;
	}


	
	
	
}
