package com.ojas.es.dao;

import java.util.List;

import com.ojas.es.entity.AddQualification;




public interface QualificationDao extends Dao<AddQualification, Long>{

	void updateQualificationsByCandidateId(Long id, List<AddQualification> education);

	Long getRoleByName(String locationName);

}
