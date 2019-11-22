package com.ojas.rpo.security.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.DashBoard.DashBoardInterface;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.transfer.ReportInput;

@Component
@Path("/submission")
public class SubmissionResourse {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DashBoardInterface submissions;

	@Autowired
	private ObjectMapper mapper;

	/*
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Path("/getDashboard/{requirementId}") public @ResponseBody Response
	 * getdata(@PathParam("requirementId") Long requirementId) {
	 * 
	 * List list=submissions.getData1(requirementId);
	 * 
	 * if(list!=null) {
	 * 
	 * return new Response(ExceptionMessage.StatusSuccess,list); } else { return
	 * new Response(ExceptionMessage.ExcepcetdDataNotAvilable); } }
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/submissionsVsrejections/{loginId}/{role}")
	public Response getAllSubmissionsAndRejections(@PathParam("loginId") Long loginId, @PathParam("role") String role,
			ReportInput reportInput) {

		try {

			Integer monthVal = null;
			Integer yearVal = null;
			if (reportInput.getTypeOfReport().equalsIgnoreCase("Yearly")
					|| reportInput.getTypeOfReport().equalsIgnoreCase("Monthly")) {

				if (null != reportInput.getYear() && !reportInput.getYear().trim().isEmpty()
						&& !reportInput.getYear().equalsIgnoreCase("null")
						&& !reportInput.getYear().equalsIgnoreCase("undefined")) {
					yearVal = Integer.valueOf(reportInput.getYear());
				} else {
					return new Response(ExceptionMessage.Exception, "500",
							"Please ensure that the values of 'year'  should be numeric");
				}
			}

			if (reportInput.getTypeOfReport().equalsIgnoreCase("Yearly")) {
				reportInput.setMonth(null);
			}

			if (null != reportInput.getMonth() && !reportInput.getMonth().trim().isEmpty() && !reportInput.getMonth().equalsIgnoreCase("null")
					&& !reportInput.getMonth().equalsIgnoreCase("undefined")) {
				monthVal = Integer.valueOf(reportInput.getMonth());
			}

			return this.submissions.getSubmissionsAndRejections(loginId, role, yearVal, monthVal, reportInput.getTypeOfReport(),
					reportInput.getDateString());

		} catch (Exception e) {

			return new Response(ExceptionMessage.Exception, "500", e.getMessage());
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/submissionsVsClosures")
	public Response getAllSubmissionsAndClosures() {
		return this.submissions.getSubmissionsAndClosures();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ClosuresVsJoinings")
	public Response getAllClosuresAndJoinings() {
		return this.submissions.getClosuresAndJoining();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/RequirementsVsSubmissions")
	public Response getAllRequirementsAndSubmissions() {
		return this.submissions.getRequirementAndSubmissions();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/RequirementsVsBdm/{id}")
	public @ResponseBody Response getRequirementsAndBdmData(@PathParam("id") Long bdm_id) {
		if (bdm_id == null) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}
		return this.submissions.getRequirementsAndBdm(bdm_id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/SubmissionBbyRecruiter/{id}")
	public @ResponseBody Response getRecruiterAndSubmision(@PathParam("id") Long recruiter_Id) {
		if (recruiter_Id == null) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}
		return this.submissions.getSubmissionsByRecruiterId(recruiter_Id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ClosureByRecruiter/{id}")
	public @ResponseBody Response getClosureByRecruiter(@PathParam("id") Long recruiter_Id) {
		if (recruiter_Id == null) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}
		return this.submissions.getClosureByRecruiterId(recruiter_Id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/JoiningsByRecruiter/{id}")
	public @ResponseBody Response getJoiningByRecruiter(@PathParam("id") Long recruiter_Id) {
		if (recruiter_Id == null) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}
		return this.submissions.getJoiningByRecruiterId(recruiter_Id);
	}
}
