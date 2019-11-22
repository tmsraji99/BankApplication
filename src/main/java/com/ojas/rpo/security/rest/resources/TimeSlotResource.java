package com.ojas.rpo.security.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.timeslots.TimeSlotDao;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.entity.CandidateStatusDTO;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.TimeSlot;

@Component
@Path("/timeSlots")
public class TimeSlotResource {

	@Autowired
	private CandidatelistDao candidateDao;

	@Autowired
	private TimeSlotDao timeSlotDao;

	@Autowired
	private InterviewDetailsDao interviewDetailsDao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(CandidateMapping timeslot) {

		CandidateStatusDTO candidateStatusDTO = new CandidateStatusDTO();
		candidateStatusDTO.setCandidateId(timeslot.getCandidate().getId());
		candidateStatusDTO.setRequirementId(timeslot.getBdmReq().getId());
		candidateStatusDTO.setReason(timeslot.getReason());
		candidateStatusDTO.setLoginId(timeslot.getMappedUser().getId());
		candidateDao.updateCandiate(candidateStatusDTO, "Interview Schedule Rejected By Candidate");
		// candidateDao.save(candidate);
		// Long candidateId=candidate.getId();
		// InterviewDetails
		// interviewDetails=interviewDetailsDao.getInterviewDetailsByCandidateId(candidateId,timeslot.getRequirement().getId(),timeslot.getUserId());
		// interviewDetails.setStatus("InActive");
		return new Response(ExceptionMessage.StatusSuccess, timeslot);

	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, TimeSlot timeslot) {
		timeslot.setId(id);
		if (this.timeSlotDao.save(timeslot) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, timeSlotDao);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response getInterviewDetailsById(@PathParam("id") Long id) {

		TimeSlot timeslots = this.timeSlotDao.find(id);

		if (timeslots == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, timeslots);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)

	public @ResponseBody Response getAllInterviewDetails() {

		List<TimeSlot> timeslots = this.timeSlotDao.findAll();

		if (timeslots == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, timeslots);
	}

	// candidateInterviewSchedule status

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("candidateInterviewScheduleStatus/{candidateId}/{reqId}")
	public @ResponseBody Response candidateInterviewScheduleStatus(@PathParam("candidateId") Long cid,
			@PathParam("reqId") Long rid, TimeSlot timeSlot) {
		System.out.println("==========inside post method====RPO");
		// Candidate candidate = this.candidateDao.find(cid);

		// candidate.setId(cid);

		// candidate.setCandidateInterviewScheduleStatus(timeSlot.getCandidateInterviewScheduleStatus());

		// if
		// (candidate.getCandidateInterviewScheduleStatus().equalsIgnoreCase("accepted"))
		// {
		// candidate.setCandidateStatus("Interview Scheduled");

		// this.candidateDao.save(candidate);
		// } else if
		// (candidate.getCandidateInterviewScheduleStatus().equalsIgnoreCase("rejected"))
		// {
		// candidate.setCandidateStatus("Scheduled Time Rejected By Recruiter");
		// candidate.setReason(timeSlot.getReason());
		// candidate.setSlot1Date(timeSlot.getSlot1Date());
		// candidate.setSlot1Time(timeSlot.getSlot1Time());
		// candidate.setSlot2Date(timeSlot.getSlot2Date());
		// candidate.setSlot2Time(timeSlot.getSlot2Time());
		// candidate.setSlot3Date(timeSlot.getSlot3Date());
		// candidate.setSlot3Time(timeSlot.getSlot3Time());
		// this.candidateDao.save(candidate);
		// this.timeSlotDao.save(timeSlot);
		// }
		// if (this.candidateDao.save(candidate) == null) {
		// return new Response(ExceptionMessage.DataIsNotSaved);
		// } else {
		// return new Response(ExceptionMessage.StatusSuccess, candidate);
		//
		// }
		return null;
	}

	@GET

	@Produces(MediaType.APPLICATION_JSON)

	@Consumes(MediaType.APPLICATION_JSON)

	@Path("getCandidateTimeSlot/{candidateId}/{reqId}")
	public @ResponseBody Response getCandidateTimeSlot(@PathParam("candidateId") Long cid,
			@PathParam("reqId") Long rid) {
		System.out.println("==========inside post method====RPO");

		TimeSlot timeslot = this.timeSlotDao.getTimeSlotsByCandiateId(cid, rid);

		if (timeslot == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, timeslot);

		}
	}

}
