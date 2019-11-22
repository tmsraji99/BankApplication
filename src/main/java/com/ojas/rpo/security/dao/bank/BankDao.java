package com.ojas.rpo.security.dao.bank;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BankAndAddressDetails;
import com.ojas.rpo.security.entity.BankDTO;
import com.ojas.rpo.security.entity.CandidateStatusDTO;



public interface BankDao extends Dao<BankAndAddressDetails, Long>{
	
	boolean updateBank(BankDTO bankDto);
	

}
