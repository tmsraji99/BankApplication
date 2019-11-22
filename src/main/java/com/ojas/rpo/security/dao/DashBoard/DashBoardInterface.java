package com.ojas.rpo.security.dao.DashBoard;

import java.util.Date;

import com.ojas.rpo.security.entity.Response;

public interface DashBoardInterface {

	// public List getData1(Long requirementID);
	// public List getData(Long requriterID);
	
	public Response getSubmissionsAndRejections(Long loginId, String role, Integer year, Integer month,String typeOfReport,Date date);

	public Response getSubmissionsAndClosures();

	public Response getClosuresAndJoining();

	public Response getRequirementAndSubmissions();

	public Response getRequirementsAndBdm(Long bdm_id);

	public Response getSubmissionsByRecruiterId(Long recruiter_Id);

	public Response getClosureByRecruiterId(Long recruiter_Id);

	public Response getJoiningByRecruiterId(Long recruiter_Id);
}
