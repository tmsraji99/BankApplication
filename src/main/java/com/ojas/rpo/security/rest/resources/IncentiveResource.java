package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.candidateReqMapping.CandidateReqMappingDao;
import com.ojas.rpo.security.dao.incentive.IncentiveDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.IncentiveNew;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.User;

@Component
@Path("/incentive")
public class IncentiveResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IncentiveDao incentiveDao;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CandidateReqMappingDao candidateReqMappingDao;


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(IncentiveNew incentive) {
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + incentive);

		if (this.incentiveDao.save(incentive) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, incentive);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getList() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<IncentiveNew> add = this.incentiveDao.findAll();
		if (add == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (add.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, add);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, IncentiveNew incentive) {
		System.out.println("==========inside post method====RPO");
		incentive.setId(id);
		this.logger.info("update(): " + incentive);

		if (this.incentiveDao.save(incentive) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, incentive);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public IncentiveNew deleteSkill(@PathParam("id") Long id) {

		this.logger.info("delete(id)");
		IncentiveNew incentive = this.incentiveDao.find(id);

		return this.incentiveDao.save(incentive);

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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(value = "{id}/{forall}")
	public @ResponseBody Response calIncentives(@PathParam("id") Long id, @PathParam("forall") String forall)
			throws IOException {
		Response response = null;
		if (forall.equalsIgnoreCase("no")) {

			if (null == id) {
				return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
			}

			response = this.incentiveDao.getRecruiterIncentive(id);
		} else {
			List<CandidateData> users = candidateReqMappingDao.getCandidatesMappingByStatus(null);

			response = this.incentiveDao.getAllRecruitersIncentives(users);
		}

		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getIncentivesList/{role}/{loginid}/{pageNo}/{pageSize}")
	public Response searchCandidatesList(@PathParam("role") String role, @PathParam("loginid") Long id,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortingOrder, @QueryParam("sortingField") String sortingField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField) {

		Response res = incentiveDao.getIncentiveList(role, id, pageNo, pageSize, sortingOrder, sortingField, searchText,
				searchField);

		return res;
	}

	// Get Recruiter incentive based on Candidate on-boarded status date is
	// doesn'tknown

}
