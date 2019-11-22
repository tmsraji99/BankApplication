package com.ojas.rpo.security.dao.Qualification;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AddQualification;


public interface QualificationDao extends Dao<AddQualification, Long>{

	void updateQualificationsByCandidateId(Long id, List<AddQualification> education);

}
