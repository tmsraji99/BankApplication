package com.ojas.rpo.security.dao.bank;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;

import com.ojas.rpo.security.entity.BankAndAddressDetails;
import com.ojas.rpo.security.entity.BankDTO;



public class JpaBankDao extends JpaDao<BankAndAddressDetails, Long> implements BankDao {

	public JpaBankDao() {
		super(BankAndAddressDetails.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean updateBank(BankDTO bankDto) {
		boolean result = false;
		
		Query q = getEntityManager().createNativeQuery(
				"update bankandaddressdetails set accountNumber=?,address=? , bankname =?,cheque = ?,iFSC = ? where id = ?");
		
		q.setParameter(1, bankDto.getAccountNumber());
		q.setParameter(2, bankDto.getAddress());
		q.setParameter(3, bankDto.getBankName());
		q.setParameter(4, bankDto.getCheque());
		q.setParameter(5, bankDto.getiFSC());
      int i = q.executeUpdate();
      if(i>0) {
    	  return true;
      }
		return result;
	}
	

}
