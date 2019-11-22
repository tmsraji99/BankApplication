package com.ojas.rpo.security.transfer;

public class CandidateAndRequirementDataMatchTransfer {

	private boolean relevantExperience;
	private boolean totalExperience;
	private boolean skills;
	private boolean expectedCTC;
	private boolean willingToRelocate;
	private boolean salaryNegotiable;
	private Integer skillsCount;
	
	public boolean isRelevantExperience() {
		return relevantExperience;
	}
	public void setRelevantExperience(boolean relevantExperience) {
		this.relevantExperience = relevantExperience;
	}
	public boolean isTotalExperience() {
		return totalExperience;
	}
	public void setTotalExperience(boolean totalExperience) {
		this.totalExperience = totalExperience;
	}
	public boolean isSkills() {
		return skills;
	}
	public void setSkills(boolean skills) {
		this.skills = skills;
	}
	public boolean isExpectedCTC() {
		return expectedCTC;
	}
	public void setExpectedCTC(boolean expectedCTC) {
		this.expectedCTC = expectedCTC;
	}
	public boolean isWillingToRelocate() {
		return willingToRelocate;
	}
	public void setWillingToRelocate(boolean willingToRelocate) {
		this.willingToRelocate = willingToRelocate;
	}
	public boolean isSalaryNegotiable() {
		return salaryNegotiable;
	}
	public void setSalaryNegotiable(boolean salaryNegotiable) {
		this.salaryNegotiable = salaryNegotiable;
	}
	public Integer getSkillsCount() {
		return skillsCount;
	}
	public void setSkillsCount(Integer skillsCount) {
		this.skillsCount = skillsCount;
	}	
	
}
