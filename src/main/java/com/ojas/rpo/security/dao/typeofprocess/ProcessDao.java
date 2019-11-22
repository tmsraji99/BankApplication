package com.ojas.rpo.security.dao.typeofprocess;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Processtype;
public interface ProcessDao extends Dao<Processtype, Long> {
	void updateCandiate(Long candiateId,String status,  Long recId,Long userId);
	Processtype findById(Long candidateId);
	void updateCandiate(Long candiateId, Long recId, Long userId);
	

}

