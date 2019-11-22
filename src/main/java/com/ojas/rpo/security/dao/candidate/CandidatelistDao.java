package com.ojas.rpo.security.dao.candidate;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateStatusDTO;
import com.ojas.rpo.security.entity.Employee;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.SearchTrackerList;

public interface CandidatelistDao extends Dao<Candidate, Long> {

	public List<Candidate> findAll(String status);

	public Response getCandiateByRequirementId(Long requiremnetId, Integer pageNo, Integer pageSize, String sortOrder, String sortField, String searchText, String searchField);

	public List<Candidate> getCandiateByRequirementId(Long requiremnetId);

	
	boolean updateCandiate(CandidateStatusDTO statusDto, String status);
	
	boolean updateCandiate(CandidateStatusDTO statusDto);

	boolean chekduplicate(String mobile, String email, String pancardNumber);

	List<Candidate> getCandiateByRecurtierId(Long recutierId);
	// List<Candidate> getCandiateByAmId(Long amId);
	// public List<User> getAmEmail();

	public Candidate getCandidateByMobileNumber(String mobile,Long loginid);

	List<Candidate> getCandiateByRecurtierIdByStatus(Long recutierId, String status);

	public List<Candidate> getCandiateBySkillName(String skillName);

	Map<String, Integer> getCandidateStatuCount(String status);

	Map<String, Integer> getCandidateStatusCountByRecruiter(String status);

	Map<String, Integer> getCandidateStatusCountByRecruiterId(Long id, String status);

	List<BdmReq> getRequiremenByCandiateId(Long requiremnetId);

	public Response getAllCandidatesByAddedPerson(Long id, Integer pageNo, Integer pageSize, String sortOrder, String sortField, String searchText, String searchField);

	public int updatingStatus(Long id, String status, String offerStatus,Long reqid,Long userId);

	public int updatingOnBoardStatus(Long id, String status, String onBoardStatus, Date onboardeddate, String ctc,String reqId,Long userId);

	public double getInsurance(Employee employee, Double age);

	int confirmBoardStatus(Long id, String onBoardStatus, Date abscondeddate,String reqId,Long userId);

	public Response updateCandidateInfo(Long id, Candidate candidate);
	
	public  Response getCandidatelistinExcel(SearchTrackerList searchTrackerList,Long clienId,Long reqid,Properties properties)throws IOException;
	
	//public  javax.ws.rs.core.Response getCandidatesMappingByStatus(SearchTrackerList searchTrackerList)throws IOException;
	

	List<Object[]> getMobileDetails(String mobile, String email, String pancardNumber);
	
		
	public Response getSummaryReport(Long bdmReq_id) ;
	
}
