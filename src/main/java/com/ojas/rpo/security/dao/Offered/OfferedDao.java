package com.ojas.rpo.security.dao.Offered;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.Offered;

public interface OfferedDao extends Dao<Offered, Long> {
	void updateCandiate(Long candiateId,String status);

}
