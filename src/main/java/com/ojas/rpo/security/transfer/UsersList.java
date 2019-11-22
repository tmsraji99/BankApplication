package com.ojas.rpo.security.transfer;

public class UsersList {
	
	public java.util.List<UserListTransfer> getList() {
		return list;
	}

	public void setList(java.util.List<UserListTransfer> list) {
		this.list = list;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	java.util.List<UserListTransfer> list;
	
	Integer totalPages;
	
	Integer totalRecords;
	
	
	
	
	

}
