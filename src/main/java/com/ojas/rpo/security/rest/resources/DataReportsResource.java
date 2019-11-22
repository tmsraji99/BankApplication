package com.ojas.rpo.security.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ojas.rpo.security.dao.datareports.DataReportsDao;
import com.ojas.rpo.security.dao.datareports.ReportsBarChartTemplates;
import com.ojas.rpo.security.entity.ReportInputDataDTO;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/barchat")
public class DataReportsResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${fileUploadPath}")
	private String documentsfolder;

	@Autowired
	private DataReportsDao reportsDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getTotalSubmissionsAndRejections/{loginId}/{role}")
	public Response getSubmittedAndRejected(@PathParam("loginId") Long loginId, @PathParam("role") String role) {
		return reportsDao.getTotalSubmittedAndRejected(loginId, role);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getSubmissionsVsRejectionsByReportType")
	public Response getSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {
		return reportsDao.getSubmissionsVsRejectionsByReportType(inputDto);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getSubmissionsVsClosersByReportType")
	public Response getSubmissionsVsClosersByReportType(ReportInputDataDTO inputDto) {
		return reportsDao.getSubmissionsVsClosuresByReportType(inputDto);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getClosersVsJoiningByReportType")
	public Response getClosersVsJoiningByReportType(ReportInputDataDTO inputDto) {
		return reportsDao.getClosuresVsJoiningByReportType(inputDto);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRecruiterWiseSubmissionsVsRejectionsByReportType")
	public Response getRecruiterWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {
		return reportsDao.getRecruiterWiseSubmissionsVsRejectionsByReportType(inputDto);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRecruiterWiseSubmissionsVsClousersByReportType")
	public Response getRecruiterWiseSubmissionsVsClousersByReportType(ReportInputDataDTO inputDto) {
		return reportsDao.getRecruiterWiseSubmissionsVsClosuresByReportType(inputDto);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRecruiterWiseClosersVsJoinersByReportType")
	public Response getRecruiterWiseClosersVsJoinersByReportType(ReportInputDataDTO inputDto) {
		return reportsDao.getRecruiterWiseClosersVsJoinersByReportType(inputDto);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getLeadWiseSubmissionsVsRejectionsByReportType")
	public Response getLeadWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {
		return reportsDao.getLeadWiseSubmissionsVsRejectionsByReportType(inputDto);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAMWiseSubmissionsVsRejectionsByReportType")
	public Response getAMWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {
		return reportsDao.getAMWiseSubmissionsVsRejectionsByReportType(inputDto);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getClosureVsJoiningByBDM")
	public String getClosuresVsJoining() {
		String submittedAndRejected = reportsDao.getClosuresVsJoining();
		return submittedAndRejected;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getSubmissionVsClosure/{loginId}/{role}")
	public Response getSubmissionsVsClosures(@PathParam("loginId") Long loginId, @PathParam("role") String role) {
		Response response = reportsDao.getSubmissionsVsClosures(loginId, role);
		return response;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getClosuresVsJoinings/{loginId}/{role}")
	public Response getClosuresVsJoinings(@PathParam("loginId") Long loginId, @PathParam("role") String role) {
		Response response = reportsDao.getClosuresVsJoinings(loginId, role);
		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRequirementWiseSubmissionsVsRejectionsByReportType")
	public Response getRequirementWiseSubmissionsVsRejectionsByReportType(ReportInputDataDTO inputDto) {
		return reportsDao.getRequirementWiseSubmissionsVsRejectionsByReportType(inputDto);
	}

}
