package com.ojas.es.dao.impl;

import java.util.List;

import com.ojas.es.dao.Dao;
import com.ojas.es.entity.AddQualification;






public interface QualificationDao extends Dao<AddQualification, Long>{

	void updateQualificationsByCandidateId(Long id, List<AddQualification> education);

	Long getRoleByName(String degree);

}
