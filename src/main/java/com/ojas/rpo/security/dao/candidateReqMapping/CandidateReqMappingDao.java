package com.ojas.rpo.security.dao.candidateReqMapping;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.CandidateStatusCounts;

public interface CandidateReqMappingDao extends Dao<CandidateMapping, Long> {

	CandidateMapping save(CandidateMapping candidateMapping);

	List<CandidateMapping> getCandiateByRecurtierId(Long recutierId, String status);

	List<CandidateMapping> getCandiateByRequirementId(Long requimentId, String Canstatus, String reqStatus);

	List<CandidateStatusCounts> getCandiateStatusByRequirementId(Long requimentId);

	List<CandidateStatusCounts> getCandiateStatusByRequirements();

	List<CandidateStatusCounts> getCandiatesStatusCountByBdmReqId();

	void getRemoveMapping(Long id);

	List<CandidateData> findAllCanditaes(String role, Long loginid);

	List<CandidateMapping> getCandiateMapCandedateId(Long candidateId, Long requirementId, Long userId);

	Response getCandidatesList(String role, Long loginid, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField);

	Response searchCandidatesList(String role, Long id, String searchInput, String searchField, Integer pageNo,
			Integer pageSize);

	CandidateMapping findMappedCandidate(Long candidateId, Long reqId, Long userId);

	Response changeCandidateMapping(CandidateMapping candidateMapping);

	Response getCandidatesBoradedList(String role, Long id, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField);

	List<CandidateData> getCandidatesMappingByStatus(String status);

	/* void (deleteMappingByRecrui); */

}
