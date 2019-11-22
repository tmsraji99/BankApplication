package com.ojas.rpo.security.entity;

public class CtcDetails {
	
	private Deduction deduction;
	
	private EmployeeEarning employeeEarning;
	
	private Employee employee;

	public Deduction getDeduction() {
		return deduction;
	}

	public void setDeduction(Deduction deduction) {
		this.deduction = deduction;
	}

	public EmployeeEarning getEmployeeEarning() {
		return employeeEarning;
	}

	public void setEmployeeEarning(EmployeeEarning employeeEarning) {
		this.employeeEarning = employeeEarning;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
