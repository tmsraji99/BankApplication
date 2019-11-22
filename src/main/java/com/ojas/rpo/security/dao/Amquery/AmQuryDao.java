package com.ojas.rpo.security.dao.Amquery;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Amquery;
import com.ojas.rpo.security.entity.Amrejected;
import com.ojas.rpo.security.entity.User;

public interface AmQuryDao extends Dao<Amquery, Long>{
	void updateCandiate(Long candiateId,String status);
	Amquery findById(Long candidateId);
}