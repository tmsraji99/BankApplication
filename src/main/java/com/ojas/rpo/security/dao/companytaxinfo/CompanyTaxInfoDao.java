package com.ojas.rpo.security.dao.companytaxinfo;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CompanyTaxInfo;

public interface CompanyTaxInfoDao extends Dao<CompanyTaxInfo, Long> {
	public List<CompanyTaxInfo> findAll();
}
