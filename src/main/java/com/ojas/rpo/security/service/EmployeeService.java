package com.ojas.rpo.security.service;

import java.text.ParseException;

import com.ojas.rpo.security.entity.Deduction;
import com.ojas.rpo.security.entity.Employee;
import com.ojas.rpo.security.entity.EmployeeEarning;




public interface EmployeeService {
	
	EmployeeEarning getEmployeeEarnings(Employee employee) throws ParseException;

	Deduction getEmployeeDeductions(Employee employee,EmployeeEarning employeeEarning);

}
