package com.ojas.rpo.security.dao.companyAddressinfo;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BankAndAddressDetails;
import com.ojas.rpo.security.entity.CompanyTaxInfo;

public interface CompanyAddressInfoDao  extends Dao<BankAndAddressDetails, Long> {
	public List<BankAndAddressDetails> findAll();
}
