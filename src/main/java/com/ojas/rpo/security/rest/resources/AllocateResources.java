package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.assign.AssignDao;
import com.ojas.rpo.security.dao.bdmreqdtls.BdmReqDao;
import com.ojas.rpo.security.dao.client.ClientDao;
import com.ojas.rpo.security.entity.Assign;
import com.ojas.rpo.security.entity.AssignTransferObject;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;

@Component
@Path("/allocaterequirment")
public class AllocateResources {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AssignDao assignDao;

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private OutlookMailSender mailSender;

	@Autowired
	private BdmReqDao requirementDao;

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Assign getAssign(@PathParam("id") Long id) {
		// Long userId=Long.parseLong(userIdentifier);
		System.out.println("==========inside post method====RPO" + id);
		this.logger.info("find(): " + id);

		return this.assignDao.find(id);
	}// getAssignList() closing

	@GET
	@Path("/assign/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readRequitrementDetails(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		List<Assign> assigndetails = this.assignDao.findById(id);
		if (assigndetails == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, assigndetails);
		}
	}

	@GET
	@Path("/getReqByRecIdandClientId/{userId}/{clientId}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReqByRecIdandClientId(@PathParam("userId") Long userId, @PathParam("clientId") Long clientId,
			@PathParam("status") String status) {
		this.logger.info("read(getReqByRecIdandClientId)");

		List<BdmReq> BdmReqAssignDetails = this.assignDao.getReqByRecIdandClientId(userId, clientId, status);
		if (BdmReqAssignDetails == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, BdmReqAssignDetails);
		}
	}

	@GET
	@Path("/getReqByRecIdandUserId/{userId}/{status}/{pageNo}/{pageSize}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReqByRecIdandUserId(@PathParam("userId") Long userId, @PathParam("status") String status,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortOrder, @QueryParam("sortingField") String sortField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField) {
		this.logger.info("read(getReqByRecIdandClientId)");

		return this.assignDao.getReqByRecIdandUserId(userId, status, pageNo, pageSize, sortOrder, sortField, searchText,
				searchField);
	}

	@GET
	@Path("/getClientsByRecById/{userId}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClientsByRecById(@PathParam("userId") Long userId, @PathParam("status") String status) {
		this.logger.info("read(getClientsByRecById)");

		List<Client> ClientDetails = this.assignDao.getClientsByRecById(userId, status);
		if (ClientDetails == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, ClientDetails);
		}
	}

	@GET
	@Path("/getAssigenByBdmId/{userId}/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAssigenByBdmId(@PathParam("userId") Long userId, @PathParam("role") String role) {
		this.logger.info("read(getClientsByRecById)");

		List<Assign> assigndetails = this.assignDao.getAssigenByBdmId(userId, role);
		if (assigndetails == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, assigndetails);
		}
	}

	@GET
	@Path("/getAssinedRequirementsByBdmId/{userId}/{role}/{pageNo}/{pageSize}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAssinedRequirementsByBdmId(@PathParam("userId") Long id, @PathParam("role") String role,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortingOrder, @QueryParam("sortingField") String sortingField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField) {

		if (null == role || role.isEmpty()) {
			return new Response(ExceptionMessage.valueOf("value role 'role' not specified"));
		}

		if (null == id) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}
		return this.assignDao.getAssinedRequirementsByBdmId(id, role, pageNo, pageSize, sortingOrder, sortingField,
				searchText, searchField);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchAssignedRequirements/{role}/{id}/{searchInput}/{searchField}/{pageNo}/{pageSize}")
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
			return new Response(ExceptionMessage.valueOf("name of 'Search Field' not specified"));
		}

		return this.assignDao.searchAssignedRequirementsByBdmId(role, id, searchInput, searchField, pageNo, pageSize);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Assign> list() throws IOException {
		this.logger.info("list()");

		/*
		 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
		 * this.mapper.writerWithView(JsonViews.Admin.class); } else {
		 * viewWriter = this.mapper.writerWithView(JsonViews.User.class); }
		 */
		/* List<Assign> allEntries = this.assignDao.findAll(); */
		return this.assignDao.findAll();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response allocateRequirment(AssignTransferObject assign, @Context HttpServletRequest request) {
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + assign);
		StringBuilder toMails = null;
		StringBuilder ccMails = null;
		Assign a = null;
		StringBuilder usersListMsgBody = null;
		boolean flag = false;
		if (assign.getUsers() != null && !assign.getUsers().isEmpty()) {
			for (User user : assign.getUsers()) {
				Assign assignObj = new Assign();
				BeanUtils.copyProperties(assign, assignObj);
				assignObj.setUsers(user);
				Integer i = this.assignDao.deleteAssignmentByRecriuterAndRequirement(assign.getBdmReq().getId(), user);

				if (i.intValue() == 0) {
					flag = true;
					a = this.assignDao.save(assignObj);
					if (toMails == null) {
						toMails = new StringBuilder(a.getUsers().getEmail());
					} else {
						toMails.append("," + a.getUsers().getEmail());
					}

					if (ccMails == null) {
						ccMails = new StringBuilder(a.getUsers().getReportingId().getEmail());
					} else {
						ccMails.append("," + a.getUsers().getReportingId().getEmail());
					}

					if (null == usersListMsgBody) {
						usersListMsgBody = new StringBuilder("<tr><td>" + a.getUsers().getId() + "</td>" + "<td>"
								+ a.getUsers().getFirstName() + " " + a.getUsers().getLastName() + "</td></tr>");
					} else {
						usersListMsgBody.append("<tr><td>" + a.getUsers().getId() + "</td>" + "<td>"
								+ a.getUsers().getFirstName() + " " + a.getUsers().getLastName() + "</td></tr>");
					}

				}
			}
			if (flag) {
				Client c = clientDao.find(assign.getClient().getId());
				/*
				 * ccMails.append("," + c.getPrimaryContact().getEmail());
				 * ccMails.append("," + c.getSecondaryContact().getEmail());
				 * ccMails.append("," + c.getAccountManger().getEmail());
				 */

				BdmReq r = requirementDao.find(assign.getBdmReq().getId());

				// Email Sending Part
				EmailEntity emailEntity = ReadPropertiesFile.readConfig();
				emailEntity.setMessagesubject(" Requirement Assigned || Customer: " + c.getClientName()
						+ " || Requirement: " + r.getNameOfRequirement());
				emailEntity.setCc(ccMails.toString());
				emailEntity.setTo(toMails.toString());
				emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");

				String msgBody = "<i> Requirement Assigned with following details </i><br><br>" + "<div>"
						+ "<table border = 1>"
						+ "<tr><th  colspan=\"2\"  style = \" background-color: #ffb84d; \">Assignment Details</th></tr>"
						+ "<tbody>" + "<tr><td><b style = \" color:#0044cc; \" >Ojas Customer ID </b></td><td>"
						+ c.getId() + "</td></tr>"
						+ "<tr><td><b style = \" color:#0044cc; \" >Customer Name </b></td><td>" + c.getClientName()
						+ "</td></tr>" + "<tr><td><b style = \" color:#0044cc; \" >Requirement ID </b></td><td>"
						+ r.getId() + "</td></tr>"
						+ "<tr><td><b style = \" color:#0044cc; \" > Requirement Name </b></td><td>"
						+ r.getNameOfRequirement() + "</td></tr>"
						+ "<tr><td><b style = \" color:#0044cc; \" > Requirement Start Date </b></td><td>"
						+ r.getRequirementStartdate() + "</td></tr>"
						+ "<tr><td><b style = \" color:#0044cc; \" > Requirement End Date </b></td><td>"
						+ r.getRequirementEndDate() + "</td></tr>" + "</tbody>" + "</table>" + "</div><br><br>"
						+ "<i><b>Assigned Recruiters : </b></i><br>" + "<div>" + "<table border = 1>"
						+ "<tr style = \" background-color: #ffb84d; \"><th>Employee ID</th><th>Employee Name</th></tr>"
						+ "<tbody>" + usersListMsgBody + "</tbody>" + "</table>" + "</div><br><br>"
						+ "<b>With Regards :</b>" + "<br><img src= \"cid:image\" alt=" + " Logo Not Available "
						+ " width=" + "160" + " " + "height=" + " " + "80>";

				emailEntity.setMessageBody(msgBody);
				mailSender.sendMail(emailEntity);
			}
		}
		if (!flag) {
			return new Response(ExceptionMessage.DuplicateRecord, assign);
		}

		return new Response(ExceptionMessage.StatusSuccess, assign);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public @ResponseBody Response active(@PathParam("id") Long id) {

		this.logger.info("delete(id)");
		int i = this.assignDao.deleteByid(id);
		if (i > 0) {
			return new Response(ExceptionMessage.StatusSuccess);
		}
		return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, Assign assign) {
		assign.setId(id);
		this.logger.info("update(): " + assign);

		if (this.assignDao.save(assign) == null) {
			return new Response(ExceptionMessage.DuplicateRecord);
		}

		else {
			this.assignDao.save(assign);
			return new Response(ExceptionMessage.StatusSuccess, assign);
		}

		// return this.assignDao.save(assign);
	}// update() closing

	/*
	 * private boolean isAdmin() { Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); Object principal
	 * = authentication.getPrincipal(); if ((principal instanceof String) &&
	 * ((String) principal).equals("anonymousUser")) { return false; }
	 * UserDetails userDetails = (UserDetails) principal;
	 * 
	 * return userDetails.getAuthorities().contains(Role.ADMIN); }// isAdmin()
	 * closing
	 */
	/*
	 * private UserDetails getUserDetails() {
	 * System.out.println("working fine ******************"); Authentication
	 * authentication = SecurityContextHolder.getContext().getAuthentication();
	 * Object principal = authentication.getPrincipal(); if ((principal
	 * instanceof String) && ((String) principal).equals("anonymousUser")) {
	 * return null; } UserDetails userDetails = (UserDetails) principal;
	 * System.out.println("working fine ******************"); return
	 * userDetails; }
	 */

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}// class closing
