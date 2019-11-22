package com.ojas.rpo.security.dao.datareports;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.entity.ReportInputDataDTO;
import com.ojas.rpo.security.entity.Response;

public interface DataReportsDao extends Dao<CandidateMapping, Long> {

	public Response getTotalSubmittedAndRejected(Long loginId,String role);

	public String getClosuresVsJoining();

	public Response getSubmissionsVsClosures(Long loginId,String role);

	public Response getSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto);
	
	public Response getRecruiterWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto);

	public Response getLeadWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto);

	public Response getAMWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto);

	public Response getRequirementWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto);

	public Response getClosuresVsJoinings(Long loginId, String role);

	Response getSubmissionsVsClosuresByReportType(ReportInputDataDTO inputDto);

	Response getClosuresVsJoiningByReportType(ReportInputDataDTO inputDto);
	
	public Response getRecruiterWiseClosersVsJoinersByReportType(ReportInputDataDTO inputDto) ;
	
	public Response getRecruiterWiseSubmissionsVsClosuresByReportType(ReportInputDataDTO inputDto) ;


}
