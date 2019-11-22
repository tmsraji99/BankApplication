package com.ojas.rpo.security.dao.bdmreqdtls;

import java.util.List;
import java.util.Map;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.CandidateStatusCounts;

public interface BdmReqDao extends Dao<BdmReq, Long>

{
	Response findreqByClientId(Long id, String status, Integer pageNo, Integer pageSize, String sortOrder, String sortField, String searchText, String searchField);

	Map<String, Integer> getReqStatusListByCount();

	List<BdmReq> findreqByStatus(String status);

	List<BdmReq> BdmReqBasedOnPrimryBDM(Long id, String status);

	List<BdmReq> getBdmReqByRole(Long id, String role);
	
	List<CandidateStatusCounts> getBdmReqCountAndStatus();
	
	Response getBdmReqirementsByRole(Long id, String role,Integer pageNo, Integer pageSize, String sortingOrder, String sortingField, String searchText, String searchField);

	Response searchRequirements(String role, Long id, String searchInput, String searchField, Integer pageNo, Integer pageSize);

	Response compareCandidateAndRequirement(Candidate cand, BdmReq bdmReq);

	Response findreqByClientId(Long id, String status);
	
	Response searchSkills(Integer pageNo, Integer pageSize,  String searchText);
	
	Response searchQualification(Integer pageNo, Integer pageSize,  String searchText);
	
	Response searchLocations(Integer pageNo, Integer pageSize,  String searchText);
		
}
