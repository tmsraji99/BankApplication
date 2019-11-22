package com.ojas.rpo.security.rest.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.candidateReqMapping.CandidateReqMappingDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.util.CandidateStatusCounts;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;

@Component
@Path("/candidateReqMapping")
public class CandidateReqMappingResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CandidateReqMappingDao candidateReqMappingDao;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private CandidatelistDao candidateDao;
	@Autowired
	private InterviewDetailsDao interviewDetailsDao;
	@Autowired
	private OutlookMailSender mailSender;
	@Autowired
	private UserDao userDao;

	@Value("${fileUploadPath}")
	private String documentsfolder;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(CandidateMapping candidateMapping, @Context HttpServletRequest request) {
		this.logger.info("save()");
		Response response = null;
		CandidateMapping cm = candidateReqMappingDao.findMappedCandidate(candidateMapping.getCandidate().getId(),
				candidateMapping.getBdmReq().getId(), candidateMapping.getMappedUser().getId());
		if (cm != null) {
			Response response1 = new Response();
			response1.setErrorMessage("This Candidate already mapped to this requirement");
			return response1;
		}

		if (candidateMapping != null) {
			Candidate can = candidateDao.find(candidateMapping.getCandidate().getId());
			candidateMapping.setCandidate(can);
			candidateMapping.setMappedUser(candidateMapping.getMappedUser());
			response = candidateReqMappingDao.changeCandidateMapping(candidateMapping);
		}

		CandidateMapping cm2 = candidateReqMappingDao.findMappedCandidate(candidateMapping.getCandidate().getId(),
				candidateMapping.getBdmReq().getId(), candidateMapping.getMappedUser().getId());

		// Mail Sending
		try {
			StringBuilder toMails = null;
			StringBuilder ccMails = null;
			StringBuilder ccMails1 = new StringBuilder();
			String clientname = null;
			if (null != cm2.getCandidate()) {
				if (null != cm2.getCandidate().getUser()) {
					toMails = new StringBuilder(cm2.getCandidate().getUser().getEmail());
				}
				if (null != cm2.getCandidate().getUser().getReportingId()) {
					toMails.append("," + cm2.getCandidate().getUser().getReportingId().getEmail());
				}
			}

			if (null != cm2.getBdmReq()) {
				Client client = cm2.getBdmReq().getClient();
				clientname = client.getClientName();

				BdmReq req = cm2.getBdmReq();
				if (null != client) {
					String primaryContact = userDao.find(req.getAddContact().getPrimaryContact_id()).getEmail();
					String secondaryContact = userDao.find(req.getAddContact().getLead_id()).getEmail();
					String accManager = userDao.find(req.getAddContact().getAccountManger_id()).getEmail();
					ccMails1.append("," + primaryContact + "," + secondaryContact + "," + accManager);

				}
			}
			Candidate c = cm2.getCandidate();
			String candidateName = c.getFirstName() + " " + c.getLastName();
			EmailEntity emailEntity = ReadPropertiesFile.readConfig();// to read
																		// config
																		// file
			emailEntity.setMessagesubject(
					"Candidate Mapped to Requirement || Candidate Name : " + candidateName + " || Requirement : ");// add
																													// subject
			emailEntity.setTo(toMails.toString());
			emailEntity.setCc(ccMails1.toString());
			emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");

			User user = cm2.mappedUser;
			String userName = null;
			if (null != user) {
				userName = user.getFirstName() + " " + user.getLastName();
			}
			String mes = "Dear all, <br> Please consider below candidate mapping details." + "<div>"
					+ "<table border = 1>"
					+ "<tr><th  colspan=\"2\"  style = \" background-color: #ffb84d; \">Candidate Mapping Details</th></tr>"
					+ "<tbody>" + "<tr><td><b>Candidate ID</b></td><td>" + c.getId() + "</td></tr>"
					+ "<tr><td><b>Candidate Name</b></td><td>" + candidateName + "</td></tr>"
					+ "<tr><td><b>Customer</b></td><td>" + clientname + "</td></tr>"
					+ "<tr><td><b>Requirement ID</b></td><td>" + cm2.getBdmReq().getId() + "</td></tr>"
					+ "<tr><td><b>Requirement Name</b></td><td>" + cm2.getBdmReq().getNameOfRequirement() + "</td></tr>"
					+ "<tr><td><b>Date of Mapping</b></td><td>" + LocalDate.now() + "</td></tr>"
					+ "<tr><td><b>Mapped By</b></td><td>" + userName + "</td></tr>" + "</tbody></table></div>"
					+ "<br><br><b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt=" + " Logo Not Available "
					+ " width=" + "160" + " " + "height=" + " " + "80>";

			emailEntity.setMessageBody(mes);
			mailSender.sendMail(emailEntity);
		} catch (Exception e) {

		}

		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateByRecurtierId/{id}/{status}")
	public @ResponseBody Response getCandiateByRecurtierId(@PathParam("id") Long recutierId,
			@PathParam("status") String status) throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateMapping> candidateMapping = this.candidateReqMappingDao.getCandiateByRecurtierId(recutierId,
				status);

		if (candidateMapping == null || candidateMapping.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateMapping);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateByRequirementId/{id}/{candidateStatus}/{reqStatus}")
	public @ResponseBody Response getCandiateByRequirementId(@PathParam("id") Long requimentId,
			@PathParam("candidateStatus") String candidateStatus, @PathParam("reqStatus") String reqStatus)
			throws IOException {

		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateMapping> candidateMapping = this.candidateReqMappingDao.getCandiateByRequirementId(requimentId,
				candidateStatus, reqStatus);

		if (candidateMapping.isEmpty() || candidateMapping == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateMapping);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateStatusByRequirementId/{id}")
	public @ResponseBody Response getCandiateStatusByRequirementId(@PathParam("id") Long requimentId)
			throws IOException {

		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateStatusCounts> candidateStatusCounts = this.candidateReqMappingDao
				.getCandiateStatusByRequirementId(requimentId);

		if (candidateStatusCounts == null || candidateStatusCounts.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateStatusCounts);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateMobileNumber/{mobile}/{loginid}")
	public @ResponseBody Response getCandidateByMobileNumber(@PathParam("mobile") String mobile,
			@PathParam("loginid") Long loginid) throws IOException {

		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		Candidate candidate = this.candidateDao.getCandidateByMobileNumber(mobile, loginid);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidate);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateMapCandedateId/{id}/{reqid}/{userId}")
	public @ResponseBody Response getCandiateMapCandedateId(@PathParam("id") Long candidateId,
			@PathParam("reqid") Long reqid, @PathParam("userId") Long userId) throws IOException {

		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateMapping> candidateStatusCounts = this.candidateReqMappingDao
				.getCandiateMapCandedateId(candidateId, reqid, userId);

		if (candidateStatusCounts == null || candidateStatusCounts.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateStatusCounts);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateStatusByRequirements")
	public @ResponseBody Response getCandiateStatusByRequirements() throws IOException {

		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateStatusCounts> candidateStatusCounts = this.candidateReqMappingDao
				.getCandiateStatusByRequirements();

		if (candidateStatusCounts == null || candidateStatusCounts.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateStatusCounts);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateCountByStatus")
	public @ResponseBody Response getCandiateCountByStatus() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateStatusCounts> candidateMapping = this.candidateReqMappingDao.getCandiatesStatusCountByBdmReqId();

		if (candidateMapping == null || candidateMapping.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateMapping);

	}

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Path("/changeReqMapping/{reqId}/{candidateId}") public @ResponseBody
	 * Response changeCandidateMapping(@PathParam("reqId") Long bdmReqId,
	 * 
	 * @PathParam("candidateId") Long candidateId) throws IOException { Response
	 * response = null; Candidate can = candidateDao.find(candidateId); if (null
	 * != can) { CandidateMapping mapping =
	 * this.candidateReqMappingDao.findMappedCandidateById(candidateId); if
	 * (null != mapping) { response =
	 * this.candidateReqMappingDao.changeCandidateMapping(bdmReqId,
	 * candidateId); } else { response = new
	 * Response(ExceptionMessage.Not_Found,
	 * "No Mapping Found on Candidate with ID " + candidateId); } } else {
	 * response = new Response(ExceptionMessage.Not_Found,
	 * "No Candidate Found with ID " + candidateId); }
	 * 
	 * return response;
	 * 
	 * }
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllCandidatesDetail/{role}/{loginid}")

	public @ResponseBody Response getAllCandidatesDetails(@PathParam("role") String role,
			@PathParam("loginid") Long loginid) throws IOException {

		this.logger.info("list()");
		List<CandidateData> candidateStatusCounts = this.candidateReqMappingDao.findAllCanditaes(role, loginid);

		if (candidateStatusCounts == null || candidateStatusCounts.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			Long id = null;
			for (CandidateData candidateData : candidateStatusCounts) {
				id = Long.parseLong(candidateData.getCandidateid());
				InterviewDetails details = interviewDetailsDao.getInterviewDetailsByCandidateId(id,
						Long.parseLong(candidateData.getBdmReqId()), candidateData.getUserId());
				if (null != details) {
					candidateData.setNameOfRound(details.getNameOfRound());
				}

			}

			return new Response(ExceptionMessage.StatusSuccess, candidateStatusCounts);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandidatesList/{role}/{loginid}/{pageNo}/{pageSize}")
	public @ResponseBody Response getCandidatesList(@PathParam("role") String role, @PathParam("loginid") Long id,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortingOrder, @QueryParam("sortingField") String sortingField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField)
			throws IOException {

		this.logger.info("list()");

		if (null == role || role.isEmpty()) {
			return new Response(ExceptionMessage.valueOf("value role 'role' not specified"));
		}

		if (null == id) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}

		Response response = this.candidateReqMappingDao.getCandidatesList(role, id, pageNo, pageSize, sortingOrder,
				sortingField, searchText, searchField);

		try {
			if (response.getStatus().equals(ExceptionMessage.DataIsEmpty)
					|| response.getStatus().equals(ExceptionMessage.Exception)) {
				return response;
			} else {
				List<CandidateData> candidateslist = (List<CandidateData>) response.getResult();
				for (CandidateData candidateData : candidateslist) {
					id = Long.parseLong(candidateData.getCandidateid());
					InterviewDetails details = interviewDetailsDao.getInterviewDetailsByCandidateId(id,
							Long.parseLong(candidateData.getBdmReqId()), candidateData.getUserId());
					if (null != details) {
						candidateData.setNameOfRound(details.getNameOfRound());
					}

				}
				response.setResult(candidateslist);
				response.setStatus(ExceptionMessage.OK);
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500", "Unable to Retrieve Mapped Candidates");
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandidatesOnboardList/{role}/{loginid}/{pageNo}/{pageSize}")
	public @ResponseBody Response getCandidatesOnboardList(@PathParam("role") String role,
			@PathParam("loginid") Long id, @PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortingOrder, @QueryParam("sortingField") String sortingField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField)
			throws IOException {

		this.logger.info("list()");

		if (null == role || role.isEmpty()) {
			return new Response(ExceptionMessage.valueOf("value role 'role' not specified"));
		}

		if (null == id) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}

		Response response = this.candidateReqMappingDao.getCandidatesBoradedList(role, id, pageNo, pageSize,
				sortingOrder, sortingField, searchText, searchField);

		try {
			if (response.getStatus().equals(ExceptionMessage.DataIsEmpty)
					|| response.getStatus().equals(ExceptionMessage.Exception)) {
				return response;
			} else {
				@SuppressWarnings("unchecked")
				List<CandidateData> candidateslist = (List<CandidateData>) response.getResult();
				for (CandidateData candidateData : candidateslist) {
					id = Long.parseLong(candidateData.getCandidateid());
					InterviewDetails details = interviewDetailsDao.getInterviewDetailsByCandidateId(id,
							Long.parseLong(candidateData.getBdmReqId()), candidateData.getUserId());
					if (null != details) {
						candidateData.setNameOfRound(details.getNameOfRound());
					}

				}
				response.setResult(candidateslist);
				response.setStatus(ExceptionMessage.OK);
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500", "Unable to Retrieve Mapped Candidates");
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchCandidatesList/{role}/{id}/{searchInput}/{searchField}/{pageNo}/{pageSize}")
	public Response searchCandidatesList(@PathParam("role") String role, @PathParam("id") Long id,
			@PathParam("searchInput") String searchInput, @PathParam("searchField") String searchField,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize) {

		if (null == role || role.isEmpty()) {
			return new Response(ExceptionMessage.valueOf("value role 'role' not specified"));
		}

		if (null == id) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}

		if (null == pageNo) {
			return new Response(ExceptionMessage.valueOf("value of 'pageNo' not specified"));
		}

		if (null == pageSize) {
			return new Response(ExceptionMessage.valueOf("value of 'pageSize' not specified"));
		}

		return this.candidateReqMappingDao.searchCandidatesList(role, id, searchInput, searchField, pageNo, pageSize);
	}

	private ResponseBuilder Response() {
		return javax.ws.rs.core.Response.ok();
	}

	private boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}
