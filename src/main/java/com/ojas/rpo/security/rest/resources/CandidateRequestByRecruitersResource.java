package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.candidateReqMapping.CandidateReqMappingDao;
import com.ojas.rpo.security.dao.candidateRequest.CandidateRequestByRecruitersDao;
import com.ojas.rpo.security.dao.interviewfeedback.InterviewFeedbackDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateRequestByRecruiters;
import com.ojas.rpo.security.entity.CandidateRequestByRecruitersRequestDto;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;

@Component
@Path("/candidateRequestByRecruiters")
public class CandidateRequestByRecruitersResource {

	@Autowired
	private CandidateRequestByRecruitersDao candidateRequestByRecruitersDao;

	@Autowired
	private CandidatelistDao candidateDao;

	@Autowired
	private UserDao userDao;

	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(CandidateRequestByRecruitersRequestDto candidateRequestByRecruiters) {

		return candidateRequestByRecruitersDao.saveRequest(candidateRequestByRecruiters);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update")
	public Response update(CandidateRequestByRecruitersRequestDto candidateRequestByRecruiters) {
		CandidateRequestByRecruiters req = new CandidateRequestByRecruiters();
		Response response = null;
		BeanUtils.copyProperties(candidateRequestByRecruiters, req);
		try {
			Long requestUserId = req.getRequestedUserId();
			Long ownerUserId = req.getOwnerUserId();

			if (req.getRequestStatus().equalsIgnoreCase("approved")) {

				Candidate c = candidateDao.find(req.getCandidateId());
				if (null != c) {
					if (null != requestUserId && null != ownerUserId) {
						User requestedUser = userDao.find(requestUserId);
						User owner = userDao.find(ownerUserId);
						if (null != requestedUser && null != owner) {
							// if (c.getUser().getId().equals(owner.getId())) {
							// c.setRequestedUser(requestedUser);
							// c.setCandidateStatus("Created");
							c.setUser(requestedUser);
							candidateDao.save(c);

							// candidateReqMapDao.getRemoveMapping(c.getId());

							req.setRequestedDate(new Date());
							req = candidateRequestByRecruitersDao.save(req);
						}
					}
				}
			}

			if (req.getRequestStatus().equalsIgnoreCase("rejected")) {
				req.setRequestedDate(new Date());
				req = candidateRequestByRecruitersDao.save(req);
			}

		} catch (Exception e) {
			response = new Response(ExceptionMessage.ExcepcetdDataNotAvilable, req);

		}
		response = new Response(ExceptionMessage.StatusSuccess, req);
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllcandidateRequestByRecruiters/{pageNo}/{pageSize}/{role}/{loginid}")
	public Response getAllcandidateRequestByRecruiters(@PathParam("pageNo") Integer pageNo,
			@PathParam("pageSize") Integer pageSize, @QueryParam("sortingOrder") String sortingOrder,
			@QueryParam("sortingField") String sortingField, @QueryParam("searchText") String searchText,
			@QueryParam("searchField") String searchField, @PathParam("role") String role,
			@PathParam("loginid") Long loginid) throws IOException {

		return candidateRequestByRecruitersDao.getAllcandidateRequestByRecruiters(pageNo, pageSize, sortingOrder,
				sortingField, searchText, searchField, loginid, role);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getcandidateRequestByRecruitersId/{id}")
	public @ResponseBody Response getcandidateRequestByRecruitersId(@PathParam("id") Long id) throws IOException {

		return candidateRequestByRecruitersDao.getcandidateRequestByRecruitersId(id);

	}

}
