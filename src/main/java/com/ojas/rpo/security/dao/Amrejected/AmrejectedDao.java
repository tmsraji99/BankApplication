package com.ojas.rpo.security.dao.Amrejected;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Amrejected;
import com.ojas.rpo.security.entity.InterviewFeedback;

public interface AmrejectedDao extends Dao<Amrejected, Long> {
	void  updateCandiate(Long candiateId,String status);
}
