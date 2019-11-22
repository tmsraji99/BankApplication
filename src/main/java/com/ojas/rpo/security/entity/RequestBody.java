package com.ojas.rpo.security.entity;

public class RequestBody {
	
	private Object requestData;
	private String sortingOrder;
	private String sortingField;
	private Integer pageNo;
	private Integer pageSize;
	private String searchField;
	private String searchInput;
	
	public Object getRequestData() {
		return requestData;
	}
	public void setRequestData(Object requestData) {
		this.requestData = requestData;
	}
	public String getSortingOrder() {
		return sortingOrder;
	}
	public void setSortingOrder(String sortingOrder) {
		this.sortingOrder = sortingOrder;
	}
	public String getSortingField() {
		return sortingField;
	}
	public void setSortingField(String sortingField) {
		this.sortingField = sortingField;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchInput() {
		return searchInput;
	}
	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}
	
	

}
