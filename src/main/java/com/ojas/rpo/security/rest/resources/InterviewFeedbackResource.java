package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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
import com.ojas.rpo.security.dao.bdmreqdtls.BdmReqDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.interviewfeedback.InterviewFeedbackDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewFeedback;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.MessageEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;
import com.ojas.rpo.security.util.WhatsAppMessageSender;

@Component
@Path("/interviewfeedback")
public class InterviewFeedbackResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InterviewFeedbackDao interviewfeedbackDao;

	@Autowired
	OutlookMailSender mailSender;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private BdmReqDao bdmReqDao;

	@Autowired
	private CandidatelistDao candidateDao;

	@Autowired
	private UserDao userDao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(InterviewFeedback feedback, @Context HttpServletRequest request) {
		System.out.println("***********************" + feedback.getRecommendedProcess());
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + feedback);
		InterviewFeedback am = null;
		String feedbacks = null;
		if (feedback.getTypeofprocess().equalsIgnoreCase("Customer feedBack")) {
			feedback.setTypeofprocess("Feedback from Customer");
			feedback.setLastUpdatedDate(new Date());
			am = this.interviewfeedbackDao.save(feedback);
			if (feedback.getInterviewStatus() != null) {
				if (feedback.getInterviewStatus().equalsIgnoreCase("rejected")
						|| feedback.getInterviewStatus().equalsIgnoreCase("hold")) {
					this.interviewfeedbackDao.updateCandiate(feedback.getCandidateid(), feedback.getInterviewStatus(),
							feedback.getRequirementId(), feedback.getUserId());
				}

				else {
					feedbacks = feedback.getRecommendedProcess();
					interviewfeedbackDao.updateInterviewDetails(feedback.getCandidateid(), "In Progress",
							feedback.getRecommendedNextRound(), feedback.getRequirementId(), feedback.getUserId());
				}
			}

		}

		if (am != null) {
			if (feedback.getRecommendedProcess() != null && !feedback.getRecommendedProcess().isEmpty()) {
				feedbacks = feedback.getRecommendedProcess();
			} else {
				feedbacks = feedback.getTypeofprocess();
			}
			if (feedback.getInterviewStatus() != null) {
				if (feedback.getInterviewStatus().equalsIgnoreCase("rejected")
						|| feedback.getInterviewStatus().equalsIgnoreCase("hold")) {
					this.interviewfeedbackDao.updateCandiate(feedback.getCandidateid(), feedback.getInterviewStatus(),
							feedback.getRequirementId(), feedback.getUserId());
				} else {
					this.interviewfeedbackDao.updateCandiate(feedback.getCandidateid(), feedbacks,
							feedback.getRequirementId(), feedback.getUserId());
				}
			}

			// Feedback Mail Sending Here

			EmailEntity emailEntity = ReadPropertiesFile.readConfig();
			emailEntity.setMessagesubject("Interview Feedback");// add subject
			emailEntity.setTo(feedback.getCandidateemail());

			String round = feedback.getNameOfRound();
			String message = null;
			if (!feedback.getInterviewStatus().equalsIgnoreCase("rejected")) {
				if (null != round) {
					if (feedback.getNameOfRound().equalsIgnoreCase("clientround")) {
						round = "Client";
					}
					if (feedback.getNameOfRound().equalsIgnoreCase("hrround")) {
						round = "HR";
					}

					if (feedback.getNameOfRound().contains("hire")) {
						message = "Dear  <i><b>" + "  " + feedback.getCandidateiname() + " " + "</b></i><br><br>"
								+ "Congratulations for Completing all Interview Rounds with <b>"
								+ feedback.getCompanyname() + "<br><br><b>Thanks & Regards</b><br>"
								+ "<img src= \"cid:image\" alt=" + " Logo Not Available " + " width=" + "160" + " "
								+ "height=" + " " + "80>";
					} else {
						message = "Dear  <i><b>" + "  " + feedback.getCandidateiname() + " " + "</b></i><br><br>"
								+ " Thanks for presence to " + round + " round of Interview with "
								+ feedback.getCompanyname() + "<br>" + "Below are the feedback deatils:" + "<br><br>"
								+ "<b>Interview Status : </b>" + feedback.getInterviewStatus() + "<br>"
								+ "<b>client Feedback: </b>" + feedback.getClientFeedback() + "<br><br>"
								+ "<b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt="
								+ " Logo Not Available " + " width=" + "160" + " " + "height=" + " " + "80>";
					}

				} else {
					message = "Dear <b>" + feedback.getCandidateiname()
							+ "</b>, <br><br> Your Interview status and feedback will be shared soon. "
							+ "<br><br><b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt="
							+ " Logo Not Available " + " width=" + "160" + " " + "height=" + " " + "80>";
				}

				emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");
				emailEntity.setMessageBody(message);
				mailSender.sendMail(emailEntity);

				// Sending WhatsApp Message

				MessageEntity msgEntity = ReadPropertiesFile.readMessageConfig();
				String candidate = feedback.getCandidateiname();

				String round1 = feedback.getNameOfRound();
				String msgText = null;
				if (null != round) {
					if (feedback.getNameOfRound().equalsIgnoreCase("clientround")) {
						round = "Client";
					}
					if (feedback.getNameOfRound().equalsIgnoreCase("hrround")) {
						round = "HR";
					}

					if (feedback.getNameOfRound().contains("hire")) {
						msgText = "Dear  " + feedback.getCandidateiname() + " "
								+ ", Congratulations for Completing all Interview Rounds with"
								+ feedback.getCompanyname() + ",Thanks & Regards";

					} else {
						msgText = "Dear " + feedback.getCandidateiname() + " " + " ,Thanks for presence to " + round
								+ " round of Interview with " + feedback.getCompanyname()
								+ ", Below are the feedback deatils:" + ", Interview Status : </b>"
								+ feedback.getInterviewStatus() + ", client Feedback: </b>"
								+ feedback.getClientFeedback() + ", Thanks & Regards";
					}

				} else {
					msgText = "Dear " + feedback.getCandidateiname()
							+ ",  Your Interview status and feedback will be shared soon. " + ", Thanks & Regards";
				}

				msgEntity.setMsgText(msgText);
				Candidate candidates = candidateDao.find(feedback.getCandidateid());
				msgEntity.setToNumber(candidates.getMobile());
				WhatsAppMessageSender.sendMessage(msgEntity);

			}

			// Official Mail Sending
			BdmReq client = bdmReqDao.find(feedback.getRequirementId());
			String amMail = userDao.find(client.getAddContact().getAccountManger_id()).getEmail();
			String pcMail = userDao.find(client.getAddContact().getPrimaryContact_id()).getEmail();
			String scMail = userDao.find(client.getAddContact().getLead_id()).getEmail();

			Candidate can = candidateDao.find(feedback.getCandidateid());
			String recruiterMail = can.getUser().getEmail();
		

			String officialMessage = "<i>Please find Interview feedback below.</i></br><br>" + "<div><table border = 1>"
					+ "<tr><th  colspan=\"2\"  style = \" background-color: #ffb84d; \">Interview Feedback Details</th></tr>"
					+ "<tbody>" + "<tr><td><b>Candidate ID : </b></td>" + "<td>" + feedback.getCandidateid()
					+ "</td></tr>" + "<tr><td><b>Candidate Name : </b></td>" + "<td>" + feedback.getCandidateiname()
					+ "</td></tr>" + "<tr><td><b>Customer Name : </b></td>" + "<td>" + feedback.getCompanyname()
					+ "</td></tr>" + "<tr><td><b>Requirement ID : </b></td>" + "<td>" + feedback.getRequirementId()
					+ "</td></tr>" + "<tr><td><b>Requirement Name : </b></td>" + "<td>"
					+ bdmReqDao.find(feedback.getRequirementId()).getNameOfRequirement() + "</td></tr>"
					+ "<tr><td><b>Name Of Round : </b></td><td>" + feedback.getNameOfRound() + "</td></tr>"
					+ "<tr><td><b>Interview Status : </b></td><td>" + feedback.getInterviewStatus() + "</td></tr>"
					+ "<tr><td><b>Customer Feedback : </b></td><td>" + feedback.getClientFeedback() + "</td></tr>"
					+ "</tbody></table></div><br><br>" + "<b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt="
					+ " Logo Not Available " + " width=" + "160" + " " + "height=" + " " + "80>";

			emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");
			emailEntity.setMessageBody(officialMessage);
			emailEntity.setMessagesubject("Interview Feedback || Candidate : " + feedback.getCandidateiname()
					+ " || Requirement ID : " + feedback.getRequirementId());
			emailEntity.setTo(recruiterMail + "," + amMail + "," + pcMail + "," + scMail);
			mailSender.sendMail(emailEntity);

			return new Response(ExceptionMessage.StatusSuccess, feedbacks);
		} else {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response list() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<InterviewFeedback> add = this.interviewfeedbackDao.findAll();
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
	public @ResponseBody Response process(@PathParam("id") Long id) {
		InterviewFeedback process = null;
		process.setId(id);
		this.logger.info("update(): " + process);
		process = this.interviewfeedbackDao.save(process);

		if (process == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, process);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		InterviewFeedback process = this.interviewfeedbackDao.find(id);
		if (process == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		return new Response(ExceptionMessage.StatusSuccess, process);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/basedid/{id}")
	public @ResponseBody Response readId(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		InterviewFeedback amquery = this.interviewfeedbackDao.findById(id);
		if (amquery == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		return new Response(ExceptionMessage.StatusSuccess, amquery);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/mail/{id}")
	public @ResponseBody Response updatestatus(@PathParam("id") Long id) {

		InterviewFeedback feedback = this.interviewfeedbackDao.findById(id);

		// feedback.setTypeofprocess("IN progress");

		if (feedback != null && feedback.getInterviewStatus().equalsIgnoreCase("shortlisted")) {

			feedback.setTypeofprocess("Submitted to Customer");
			this.interviewfeedbackDao.updateCandiate(id, feedback.getTypeofprocess(), feedback.getRequirementId(),
					feedback.getUserId());

			// new MailGenaration().sendSpecificMail(feedback);
			return new Response(ExceptionMessage.StatusSuccess, feedback);
		} else if (feedback != null && feedback.getInterviewStatus().equalsIgnoreCase("selected")) {

			feedback.setTypeofprocess("waiting for Offer release");
			this.interviewfeedbackDao.updateCandiate(id, feedback.getTypeofprocess(), feedback.getRequirementId(),
					feedback.getUserId());

			// new MailGenaration().sendSpecificMail(feedback);
			return new Response(ExceptionMessage.StatusSuccess, feedback);
		} else if (feedback != null && feedback.getInterviewStatus().equalsIgnoreCase("rejected")) {

			feedback.setTypeofprocess("Candidate profile Rejeceted");
			this.interviewfeedbackDao.updateCandiate(id, feedback.getTypeofprocess(), feedback.getRequirementId(),
					feedback.getUserId());

			// new MailGenaration().sendSpecificMail(feedback);
			return new Response(ExceptionMessage.StatusSuccess, feedback);
		}

		else
			return new Response(ExceptionMessage.DataIsNotSaved);

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
