package com.ojas.rpo.security.dao.addressdetails;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AddressDetails;

public interface AddressDetailsDao extends Dao<AddressDetails,Long> {
	public List<AddressDetails> findBycpId(Long cpid);

}
