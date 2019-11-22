package com.ojas.rpo.security.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), BDM("ROLE_BDM"), RECRUITER("ROLE_RECRUITER"), AM("ROLE_AM"), TEAMLEAD(
			"ROLE_LEAD"), FINANCELEAD("ROLE_FINANCELEAD"), FINANCEEXECUTIVE("ROLE_FINANCEEXECUTIVE"),MANAGEMENT("MANAGEMENT"),MIS("MIS");
	private String authority;

	Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}
