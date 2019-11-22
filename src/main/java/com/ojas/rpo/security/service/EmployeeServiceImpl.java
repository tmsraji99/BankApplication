package com.ojas.rpo.security.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.entity.Deduction;
import com.ojas.rpo.security.entity.Employee;
import com.ojas.rpo.security.entity.EmployeeEarning;



@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private CandidatelistDao candidateDao;

	@Override
	public EmployeeEarning getEmployeeEarnings(Employee employee) throws ParseException {

		EmployeeEarning employeeEarning = new EmployeeEarning();

		// Basic is 40% of Fixed CTC
		employeeEarning.setBasic(employee.getFixedCtc() * 0.4);

		// HRA is 40% of Basic(50% for 4 Metros)
		if (employee.getIsMetroCity().equalsIgnoreCase("yes")) {
			employeeEarning.setHra(employeeEarning.getBasic() * 0.5);
		} else if (employee.getIsMetroCity().equalsIgnoreCase("no")) {
			employeeEarning.setHra(employeeEarning.getBasic() * 0.4);
		}

		// Food card is 1100 per month if total CTC less than 5 lakhs else 3000 per
		// month.
		// Bonus is FixedCtc/12 if total CTC less than 5 lakhs else 0.
		if (employee.getTotalCtc() < 500000.00) {
			employeeEarning.setFoodCard(13200.00);
			employeeEarning.setBonus(employee.getFixedCtc() / 12);
		} else {
			employeeEarning.setFoodCard(36000.00);
			employeeEarning.setBonus(0.0);
		}

		// Provident Fund is 12% of Basic
		employeeEarning.setProvidentFund(employeeEarning.getBasic() * 0.12);

		// Project Allowance and Location Allowance are VariableCTC/2 if Skill is
		// Software and Employee Type is Bench
		if (employee.getEmployeeType().equalsIgnoreCase("Bench") && employee.getSkill().equalsIgnoreCase("Software")) {
			employeeEarning.setProjectAllowance(employee.getVariableCtc() / 2);
			employeeEarning.setLocationAllowance(employee.getVariableCtc() / 2);
			employeeEarning.setPerformancePay(36000.00);
		}

		// Gratuity is 4.83% of basic
		employeeEarning.setGratuity(employeeEarning.getBasic() * 0.0483);

		List<Double> ages = getAges(employee);

		// set Insurance
		setInsurance(employee, employeeEarning, ages);

		// set Special Allowance
		employeeEarning.setSplAllowance(employee.getTotalCtc() - employeeEarning.getBasic() - employeeEarning.getHra()
				- employeeEarning.getFoodCard() - employeeEarning.getBonus() - employeeEarning.getInsurance()
				- employeeEarning.getProjectAllowance() - employeeEarning.getLocationAllowance()
				- employeeEarning.getGratuity() - employeeEarning.getProvidentFund()
				- employeeEarning.getPerformancePay());
		employeeEarning.setTotalCtc(employee.getTotalCtc());

		// set Per Month Total CTC
		employeeEarning.setPerMonthCtc(
				employeeEarning.getBasic() / 12 + employeeEarning.getHra() / 12 + employeeEarning.getSplAllowance() / 12
						+ employeeEarning.getFoodCard() / 12 + employeeEarning.getBonus() / 12
						+ employeeEarning.getInsurance() / 12 + employeeEarning.getProjectAllowance() / 12
						+ employeeEarning.getLocationAllowance() / 12 + employeeEarning.getGratuity() / 12
						+ employeeEarning.getProvidentFund() / 12 + employeeEarning.getPerformancePay() / 12);

		// set EDLI
		employeeEarning.setEdli(450);

		// set GTI
		employeeEarning.setGti(employee.getFixedCtc() * 3 * 0.85 / 1000);
		return employeeEarning;
	}

	private void setInsurance(Employee employee, EmployeeEarning employeeEarning, List<Double> ages) {

		double insuranceCover = 0.0;
		for (Double age : ages) {
			insuranceCover = insuranceCover + candidateDao.getInsurance(employee, age);
		}
		employeeEarning.setInsurance(insuranceCover + 450 + employee.getFixedCtc() * 3 * 0.85 / 1000);
	}

	/*private double getInsurance(Employee employee, Double age) {
		double insuranceCoverage = 0.0;
		double insuranceCover = 0.0;

		if (employee.getEmployeeType().equalsIgnoreCase("Spl")) {
			insuranceCoverage = 100000.00;
		} else if (employee.getTotalCtc() < 500000.00) {
			insuranceCoverage = 300000.00;

		} else if (employee.getTotalCtc() >= 500000.00 && employee.getTotalCtc() < 1000000.00) {
			insuranceCoverage = 500000.00;

		} else if (employee.getTotalCtc() >= 1000000.00) {
			insuranceCoverage = 700000.00;
		}
		if (age < 35.00) {
			String sql = "select 35Yrs from insurance where sum_insured=?";
			insuranceCover = jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage }, Double.class);
		} else if (age > 35.00 && age < 45.00) {
			String sql = "select 45Yrs from insurance where sum_insured=?";
			insuranceCover = jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage }, Double.class);
		} else if (age > 45.00 && age < 55.00) {
			String sql = "select 55Yrs from insurance where sum_insured=?";
			insuranceCover = jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage }, Double.class);
		} else if (age > 55.00 && age < 65.00) {
			String sql = "select 65Yrs from insurance where sum_insured=?";
			insuranceCover = jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage }, Double.class);
		} else if (age > 65.00 && age < 70.00) {
			String sql = "select 70Yrs from insurance where sum_insured=?";
			insuranceCover = jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage }, Double.class);
		} else if (age > 70.00 && age < 75.00) {
			String sql = "select 75Yrs from insurance where sum_insured=?";
			insuranceCover = jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage }, Double.class);
		} else if (age > 75.00 && age < 80.00) {
			String sql = "select 80Yrs from insurance where sum_insured=?";
			insuranceCover = jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage }, Double.class);
		}
		return insuranceCover;
	}*/

	private List<Double> getAges(Employee employee) throws ParseException {
		List<Double> ages = new ArrayList<Double>();
		if (!StringUtils.isEmpty(employee.getEmployeeDob())) {
			double age = getAge(employee.getJoiningDate(), employee.getEmployeeDob());
			ages.add(age);
		}

		if (!StringUtils.isEmpty(employee.getSpouseDob())) {
			double age = getAge(employee.getJoiningDate(), employee.getSpouseDob());
			ages.add(age);
		}
		if (!StringUtils.isEmpty(employee.getKid1Dob())) {
			double age = getAge(employee.getJoiningDate(), employee.getKid1Dob());
			ages.add(age);
		}
		if (!StringUtils.isEmpty(employee.getKid2Dob())) {
			double age = getAge(employee.getJoiningDate(), employee.getKid2Dob());
			ages.add(age);
		}

		if (!StringUtils.isEmpty(employee.getFatherDob())) {
			double age = getAge(employee.getJoiningDate(), employee.getFatherDob());
			ages.add(age);
		}

		if (!StringUtils.isEmpty(employee.getMotherDob())) {
			double age = getAge(employee.getJoiningDate(), employee.getMotherDob());
			ages.add(age);
		}

		return ages;
	}

	private double getAge(String joiningDate1, String employeeDob1) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date joiningDate = sdf.parse(joiningDate1);
		Date dob = sdf.parse(employeeDob1);

		long diffInMillies = Math.abs(dob.getTime() - joiningDate.getTime());
		double age = Math.round(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) / 365.0 * 100.0) / 100.0;
		return age;
	}

	/*
	 * private double getAge(Employee employee) throws ParseException {
	 * SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
	 * Date joiningDate = employee.getJoiningDate(); Date dob =
	 * employee.getEmployeeDob();
	 * 
	 * long diffInMillies = Math.abs(dob.getTime() - joiningDate.getTime()); double
	 * age = Math.round(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
	 * / 365 * 100.0) / 100.0; return age; }
	 */

	/*
	 * private void setInsurance(Employee employee, EmployeeEarning employeeEarning,
	 * double age) {
	 * 
	 * double insuranceCoverage = 0.0;
	 * 
	 * if (employee.getEmployeeType().equalsIgnoreCase("Spl")) { insuranceCoverage =
	 * 100000.00; } else if (employee.getTotalCtc() < 500000.00) { insuranceCoverage
	 * = 300000.00;
	 * 
	 * } else if (employee.getTotalCtc() < 500000.00 && employee.getTotalCtc() <
	 * 1000000.00) { insuranceCoverage = 500000.00;
	 * 
	 * } else if (employee.getTotalCtc() > 1000000.00) { insuranceCoverage =
	 * 700000.00; } if (age < 35.00) { String sql =
	 * "select 35Yrs from insurance where sum_insured=?"; double insuranceCover =
	 * jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage },
	 * Double.class); employeeEarning.setInsurance(insuranceCover); } else if (age >
	 * 35.00 && age < 45.00) { String sql =
	 * "select 45Yrs from insurance where sum_insured=?"; double insuranceCover =
	 * jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage },
	 * Double.class); employeeEarning.setInsurance(insuranceCover); } else if (age >
	 * 45.00 && age < 55.00) { String sql =
	 * "select 55Yrs from insurance where sum_insured=?"; double insuranceCover =
	 * jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage },
	 * Double.class); employeeEarning.setInsurance(insuranceCover); } else if (age >
	 * 55.00 && age < 65.00) { String sql =
	 * "select 65Yrs from insurance where sum_insured=?"; double insuranceCover =
	 * jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage },
	 * Double.class); employeeEarning.setInsurance(insuranceCover); } else if (age >
	 * 65.00 && age < 70.00) { String sql =
	 * "select 70Yrs from insurance where sum_insured=?"; double insuranceCover =
	 * jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage },
	 * Double.class); employeeEarning.setInsurance(insuranceCover); } else if (age >
	 * 70.00 && age < 75.00) { String sql =
	 * "select 75Yrs from insurance where sum_insured=?"; double insuranceCover =
	 * jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage },
	 * Double.class); employeeEarning.setInsurance(insuranceCover); } else if (age >
	 * 75.00 && age < 80.00) { String sql =
	 * "select 80Yrs from insurance where sum_insured=?"; double insuranceCover =
	 * jdbcTemplate.queryForObject(sql, new Object[] { insuranceCoverage },
	 * Double.class); employeeEarning.setInsurance(insuranceCover); }
	 * 
	 * }
	 */

	@Override
	public Deduction getEmployeeDeductions(Employee employee, EmployeeEarning employeeEarning) {

		Deduction deduction = new Deduction();

		deduction.setEmployeePf(employeeEarning.getBasic() * 0.12 / 12);
		deduction.setEmployerPf(employeeEarning.getBasic() * 0.12 / 12);
		deduction.setProfessionalTax(200.0);
		deduction.setFoodCard(employeeEarning.getFoodCard() / 12);

		if (employee.getEmployeeType().equalsIgnoreCase("Bench") && employee.getSkill().equalsIgnoreCase("Software")) {
			deduction.setProjectAllowance(employeeEarning.getProjectAllowance() / 12);
			deduction.setLocationAllowance(employeeEarning.getLocationAllowance() / 12);
		}

		deduction.setPerMonthDeduction(deduction.getEmployeePf() + deduction.getEmployerPf() + deduction.getFoodCard()
				+ deduction.getProfessionalTax() + deduction.getProjectAllowance() + deduction.getLocationAllowance());
		return deduction;
	}

}
