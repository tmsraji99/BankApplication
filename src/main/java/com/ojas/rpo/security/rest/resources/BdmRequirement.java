package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.bdmreqdtls.BdmReqDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Location;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.CandidateStatusCounts;
import com.ojas.rpo.security.util.DateParsing;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;

@Component
@Path("/Bdmrequire")
public class BdmRequirement {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	Date date = new Date();

	@Autowired
	private BdmReqDao bdmReqDao;

	@Autowired
	OutlookMailSender mailSender;

	@Autowired
	private CandidatelistDao candidatelistDao;

	/*
	 * @Autowired private User user;
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getdata() throws IOException {
		this.logger.info("list()");

		List<BdmReq> bdmReq = this.bdmReqDao.findAll();
		if (bdmReq == null) {

			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			/*
			 * if (bdmReq.size() > 0) {
			 * 
			 * ListIterator litr = bdmReq.listIterator(); SimpleDateFormat
			 * formatter = new SimpleDateFormat("dd MMMM yyyy");
			 * 
			 * while (litr.hasNext()) { BdmReq req = (BdmReq) litr.next();
			 * req.setStartDate( formatter.format(req.getRequirementStartdate()
			 * != null ? req.getRequirementStartdate() : date)); req.setEndDate(
			 * formatter.format(req.getRequirementEndDate() != null ?
			 * req.getRequirementEndDate() : date)); }
			 */
			return new Response(ExceptionMessage.StatusSuccess, bdmReq);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		BdmReq bdmReq = this.bdmReqDao.find(id);

		if (bdmReq == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
			if (bdmReq.getRequirementStartdate() != null) {
				bdmReq.setStartDate(formatter.format(bdmReq.getRequirementStartdate()));
			}
			if (bdmReq.getRequirementEndDate() != null) {
				bdmReq.setEndDate(formatter.format(bdmReq.getRequirementEndDate()));
			}
			return new Response(ExceptionMessage.StatusSuccess, bdmReq);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/clientreq/{id}/{status}/{pageNo}/{pageSize}")
	public @ResponseBody Response getdata(@PathParam("id") Long id, @PathParam("status") String status,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortOrder, @QueryParam("sortingField") String sortField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField) {
		this.logger.info("read(id)");
		Response response = this.bdmReqDao.findreqByClientId(id, status, pageNo, pageSize, sortOrder, sortField,
				searchText, searchField);

		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getdataByID/{id}/{status}")
	public @ResponseBody Response getdataByID(@PathParam("id") Long id, @PathParam("status") String status) {
		this.logger.info("read(id)");
		Response response = this.bdmReqDao.findreqByClientId(id, status);

		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/clientreqbystatus/{status}")
	public @ResponseBody Response clientReqByStatus(@PathParam("status") String status) {
		this.logger.info("read(id)");
		List<BdmReq> bdmReq = this.bdmReqDao.findreqByStatus(status);
		if (bdmReq == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {
			if (bdmReq.size() > 0) {
				ListIterator litr = bdmReq.listIterator();
				SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

				while (litr.hasNext()) {
					BdmReq req = (BdmReq) litr.next();
					req.setStartDate(formatter
							.format(req.getRequirementStartdate() != null ? req.getRequirementStartdate() : date));
					req.setEndDate(
							formatter.format(req.getRequirementEndDate() != null ? req.getRequirementEndDate() : date));
				}
			} else {
				return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
			}

			return new Response(ExceptionMessage.StatusSuccess, bdmReq);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(BdmReq bdmReq, @Context HttpServletRequest request) {
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + bdmReq);

		String s = bdmReq.getNameOfRequirement();
		String fs = DateParsing.textConvertor(s);
		bdmReq.setNameOfRequirement(fs);
		bdmReq.setLastUpdatedDate(new Date());
		BdmReq requirement = this.bdmReqDao.save(bdmReq);
		if (null == requirement) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} /*
			 * else {
			 * 
			 * Client client = requirement.getClient(); String primaryContact =
			 * client.getPrimaryContact().getEmail(); String secondaryContact =
			 * client.getSecondaryContact().getEmail(); String accManager =
			 * client.getAccountManger().getEmail(); String
			 * primaryContactReporting =
			 * client.getPrimaryContact().getReportingId().getEmail(); String
			 * secondaryContactReporting =
			 * client.getSecondaryContact().getReportingId().getEmail();
			 * 
			 * EmailEntity emailEntity = ReadPropertiesFile.readConfig();
			 * emailEntity.
			 * setMessagesubject("New Requirement Added || Customer : " +
			 * client.getClientName() + " || Requirement : " +
			 * requirement.getNameOfRequirement());
			 * emailEntity.setTo(primaryContact + "," + secondaryContact + "," +
			 * accManager); emailEntity.setCc(primaryContactReporting + "," +
			 * secondaryContactReporting);
			 * 
			 * List<Location> locationsList = requirement.getLocations();
			 * StringBuilder locationsBuilder = null; String locations = null;
			 * for (Location loc : locationsList) { if (null == locations) {
			 * locations = loc.getLocationName(); locationsBuilder = new
			 * StringBuilder(locations); } else { locationsBuilder.append("," +
			 * loc.getLocationName()); } }
			 * 
			 * String startDate = null; String endDate = null;
			 * 
			 * if (null != requirement.getRequirementStartdate()) { LocalDate
			 * startLD = new
			 * java.sql.Date(requirement.getRequirementStartdate().getTime()).
			 * toLocalDate(); startDate = startLD.getDayOfMonth() + "-" +
			 * startLD.getMonth().name() + "-" + startLD.getYear(); } if (null
			 * != requirement.getRequirementEndDate()) { LocalDate endLD = new
			 * java.sql.Date(requirement.getRequirementEndDate().getTime()).
			 * toLocalDate(); endDate = endLD.getDayOfMonth() + "-" +
			 * endLD.getMonth().name() + "-" + endLD.getYear(); }
			 * 
			 * String mes =
			 * "<i>Hi All One New Requirement is Added. </i><br><br>" + "<div>"
			 * + "<table border = 1>" +
			 * "<tr><th  colspan=\"2\"  style = \" background-color: #ffb84d; \">Customer Details</th></tr>"
			 * + "<tbody>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Ojas Requirement ID </b></td><td>"
			 * + requirement.getId() + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Requirement Name </b></td><td>"
			 * + requirement.getNameOfRequirement() + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Customer Name  </b></td><td>"
			 * + requirement.getClient().getClientName() + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Requirement Type </b></td><td>"
			 * + requirement.getTypeOfHiring() + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Location(s) </b></td><td>"
			 * + locationsBuilder.toString() + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Requirement Start Date </b></td><td>"
			 * + startDate + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Requirement End Date </b></td><td>"
			 * + endDate + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Relavent Experience </b></td><td>"
			 * + requirement.getRelavantExperience() + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Number of Positions </b></td><td>"
			 * + requirement.getNumberOfPositions() + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Notice Period </b></td><td>"
			 * + requirement.getNoticePeriod() + "</td></tr>" +
			 * "<tr><td><b style = \" color:#0044cc; \" >Customer Spoc Name </b></td><td>"
			 * + requirement.getAddContact().getContact_Name() + "</td></tr>" +
			 * "</tbody></table></div>" +
			 * "<br><i>Requirement Created By : </i><b>" +
			 * requirement.getClient().getPrimaryContact().getFirstName() + " "
			 * + requirement.getClient().getPrimaryContact().getLastName() +
			 * "</b>" + "<br><img src= \"cid:image\" alt=" +
			 * " Logo Not Available " + " width=" + "160" + " " + "height=" +
			 * " " + "80>"; emailEntity.setLogoImagePath(getAppUrl(request) +
			 * "/images/ojas-logo.png"); emailEntity.setMessageBody(mes);
			 * 
			 * mailSender.sendMail(emailEntity);
			 */

		return new Response(ExceptionMessage.StatusSuccess, bdmReq);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("BdmReqBasedOnPrimaryContactId/{id}/{status}")
	public @ResponseBody Response BdmReqBasedOnPrimaryContactId(@PathParam("id") Long id,
			@PathParam("status") String status) {
		this.logger.info("read(id)");

		List<BdmReq> bdmReq = this.bdmReqDao.BdmReqBasedOnPrimryBDM(id, status);

		if (bdmReq == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {

			return new Response(ExceptionMessage.StatusSuccess, bdmReq);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response create(@PathParam("id") Long id, BdmReq bdmReq) {
		System.out.println("==========inside post method====RPO");
		bdmReq.setId(id);
		bdmReq.setLastUpdatedDate(new Date());
		this.logger.info("update(): " + bdmReq);

		if (this.bdmReqDao.save(bdmReq) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, bdmReq);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("BdmReqCountAndStatus")
	public @ResponseBody Response BdmReqCountAndStatus() {
		this.logger.info("read(id)");

		List<CandidateStatusCounts> list = this.bdmReqDao.getBdmReqCountAndStatus();

		if (list.isEmpty()) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {

			return new Response(ExceptionMessage.StatusSuccess, list);
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public BdmReq delete(@PathParam("id") Long id) {

		this.logger.info("delete(id)");
		BdmReq bdmReq = this.bdmReqDao.find(id);
		bdmReq.setStatus("inactive");

		return this.bdmReqDao.save(bdmReq);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getReqStatusListByCount")
	public Map<String, Integer> getReqStatusListByCount() {
		Map<String, Integer> statusCounts = bdmReqDao.getReqStatusListByCount();

		return statusCounts;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("BdmReqByRole/{id}/{role}")
	public @ResponseBody Response getBdmReqByRole(@PathParam("id") Long id, @PathParam("role") String role) {

		this.logger.info("read(id)");

		List<BdmReq> bdmReq = this.bdmReqDao.getBdmReqByRole(id, role);

		if (bdmReq == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, bdmReq);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getBdmReqByRole/{id}/{role}/{pageNo}/{pageSize}")
	public @ResponseBody Response getBdmRequirementsByRole(@PathParam("id") Long id, @PathParam("role") String role,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortingOrder, @QueryParam("sortingField") String sortingField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField) {

		this.logger.info("read(id)");

		if (null == role || role.isEmpty()) {
			return new Response(ExceptionMessage.valueOf("value role 'role' not specified"));
		}

		if (null == id) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}
		return this.bdmReqDao.getBdmReqirementsByRole(id, role, pageNo, pageSize, sortingOrder, sortingField,
				searchText, searchField);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("searchRequirements/{role}/{id}/{searchInput}/{searchField}/{pageNo}/{pageSize}")
	public Response searchRequirements(@PathParam("role") String role, @PathParam("id") Long id,
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

		if (null == searchField) {
			return new Response(ExceptionMessage.valueOf("name of 'searchField' not specified"));
		}
		return this.bdmReqDao.searchRequirements(role, id, searchInput, searchField, pageNo, pageSize);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("compareCandidateAndRequirement/{candidateId}/{reqId}")
	public Response compareCandidateAndRequirement(@PathParam("candidateId") Long candidateId,
			@PathParam("reqId") Long reqId) {
		Response response = null;

		if (null != candidateId && !candidateId.equals("undefined") && null != reqId && !reqId.equals("undefined")) {
			Candidate cand = candidatelistDao.find(candidateId);
			BdmReq bdmReq = bdmReqDao.find(reqId);

			if (null != cand && null != bdmReq) {
				response = this.bdmReqDao.compareCandidateAndRequirement(cand, bdmReq);
			} else {
				if (null == cand) {
					response = new Response(ExceptionMessage.Not_Found, "Candidate Not Found with id = " + candidateId);
				}
				if (null == bdmReq) {
					response = new Response(ExceptionMessage.Not_Found, "Requirement Not Found with id = " + reqId);
				}
			}
		} else {
			response = new Response(ExceptionMessage.Bad_Request, "Candidate Id and Requirement Id both are required.");
		}
		return response;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getskills/{pageNo}/{pageSize}")
	public @ResponseBody Response searchSkills(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,			
			@QueryParam("searchText") String searchText) {
		
			return this.bdmReqDao.searchSkills(pageNo, pageSize, searchText);

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getqualification/{pageNo}/{pageSize}")
	public @ResponseBody Response searchQualification(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,			
			@QueryParam("searchText") String searchText) {
		
			return this.bdmReqDao.searchQualification(pageNo, pageSize, searchText);

	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getlocations/{pageNo}/{pageSize}")
	public @ResponseBody Response searchLocations(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,			
			@QueryParam("searchText") String searchText) {
		
			return this.bdmReqDao.searchLocations(pageNo, pageSize, searchText);

	}
	

	/*
	 * private boolean isAdmin() { Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); Object principal
	 * = authentication.getPrincipal(); if ((principal instanceof String) &&
	 * ((String) principal).equals("anonymousUser")) { return false; }
	 * UserDetails userDetails = (UserDetails) principal;
	 * 
	 * return userDetails.getAuthorities().contains(Role.ADMIN); }
	 */

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
