package com.ojas.rpo.security.dao.pastmappings;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.PastAndChangedCandidateMappings;

public class JpaPastAndChangedCandidateMappings extends JpaDao<PastAndChangedCandidateMappings, Long> implements PastAndChangedCandidateMappingsDao{
	
	public JpaPastAndChangedCandidateMappings() {
		super(PastAndChangedCandidateMappings.class);
	}

}
