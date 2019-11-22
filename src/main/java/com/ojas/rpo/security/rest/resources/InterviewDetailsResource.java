package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.interviewfeedback.InterviewFeedbackDao;
import com.ojas.rpo.security.dao.typeofprocess.ProcessDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.MessageEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;
import com.ojas.rpo.security.util.WhatsAppMessageSender;

@Component
@Path("/interviewDetails")
public class InterviewDetailsResource {

	@Autowired
	private InterviewDetailsDao interviewDetailsDao;

	@Autowired
	private InterviewFeedbackDao interviewfeedbackDao;

	@Autowired
	private ProcessDao processDao;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	OutlookMailSender mailSender;

	static final String activeStatus = "Active";

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(InterviewDetails newDetails) {

		Long candidateId = newDetails.getCandidate().getId();
		Long reqId = newDetails.getRequirement().getId();
		Long userId = newDetails.getUserId();
		try {
			InterviewDetails oldDetails = interviewDetailsDao.getInterviewDetailsByCandidateId(candidateId, reqId,
					userId);
			if (oldDetails != null) {
				oldDetails.setAddress(newDetails.getAddress());
				oldDetails.setInterviewDate(newDetails.getInterviewDate());
				oldDetails.setInterviewTime(newDetails.getInterviewTime());
				oldDetails.setInterviewLocation(newDetails.getInterviewLocation());
				oldDetails.setInterviewType(newDetails.getInterviewType());
				oldDetails.setNameOfRound(newDetails.getNameOfRound());
				oldDetails.setSpocName(newDetails.getSpocName());
				oldDetails.setLastUpdatedDate(new Date());
				oldDetails.setStatus("Active");
				interviewDetailsDao.save(oldDetails);
				this.interviewfeedbackDao.updateCandiate(candidateId,
						"Shared " + oldDetails.getNameOfRound() + " Round Interview Details with Recruiter",
						newDetails.getRequirement().getId(), newDetails.getUserId());
			} else {

				/*
				 * newDetails.setStatus("In progress"); interviewDetailsDao.save(newDetails);
				 */

				newDetails.setStatus("In progress");
				newDetails.setLastUpdatedDate(new Date());
				interviewDetailsDao.save(newDetails);
				this.interviewfeedbackDao.updateCandiate(candidateId,
						"Shared " + newDetails.getNameOfRound() + " Round Interview Details with Recruiter",
						newDetails.getRequirement().getId(), newDetails.getUserId());

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		// candidateDao.updateCandiate(details.getCandidate().getId(), "In
		// progress");

		return new Response(ExceptionMessage.StatusSuccess, newDetails);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getInterviewDetailsById/{id}/{reqId}/{userId}")
	public @ResponseBody Response getInterviewDetailsById(@PathParam("id") Long id, @PathParam("reqId") Long reqId,
			@PathParam("userId") Long userId) {

		// InterviewDetails interviewDetails =
		// this.interviewDetailsDao.find(id);
		InterviewDetails interviewDetails = this.interviewDetailsDao.getInterviewDetailsByCandidateId(id, reqId,
				userId);

		if (interviewDetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, interviewDetails);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllInterviewDetails() {

		List<InterviewDetails> interviewDetails = this.interviewDetailsDao.findAll();

		if (interviewDetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, interviewDetails);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/interviewDetailslistByCandidateId/{id}/{status}/{reqId}/{userId}")
	public @ResponseBody Response interviewDetailslistByCandidateId(@PathParam("id") Long candidateId,
			@PathParam("status") String status, @PathParam("reqId") Long reqId, @PathParam("userId") Long userId)
			throws IOException {
		List<InterviewDetails> interviewDetls = this.interviewDetailsDao
				.getInterviewDetailsByCandidateIdAndStatus(candidateId, status, reqId, userId);

		if (interviewDetls == null) { // throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, interviewDetls);
		}
	}

	@POST
	@Path("/updateInterviewStatusDetails/{id}/{reqId}/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateInterviewStatus(@PathParam("id") Long candidateId, @PathParam("reqId") Long reqId,
			@PathParam("userId") Long userId, @Context HttpServletRequest request) {
		InterviewDetails interviewDetails = interviewDetailsDao.updateInterviewStatus(candidateId, reqId, userId,
				activeStatus);
		processDao.updateCandiate(candidateId, "Waiting for Interview Feedback", reqId, userId);
		if (interviewDetails == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {

		try {
				BdmReq bdmreq = interviewDetails.getRequirement();
				if (null != bdmreq) {
					Client client = bdmreq.getClient();
					if (null != client) {
						EmailEntity emailEntity = ReadPropertiesFile.readConfig();// to
																					// read
																					// config
																					// file
						Candidate candidate = interviewDetails.getCandidate();
						emailEntity.setMessagesubject("Interview  Schedule");// add
																				// subject
						emailEntity.setTo(candidate.getEmail());

						Long primaryContactId = bdmreq.getAddContact().getPrimaryContact_id();
						Long managerContactId = bdmreq.getAddContact().getAccountManger_id();
						Long leadId=bdmreq.getAddContact().getLead_id();
						String primaryMail = userDao.find(primaryContactId).getEmail();
						String managerMail = userDao.find(managerContactId).getEmail();
						String leadMail = userDao.find(leadId).getEmail();
						//String accManager = client.getAccountManger().getEmail();
						//String primaryContactReporting = client.getPrimaryContact().getReportingId().getEmail();
						//String secondaryContactReporting = client.getSecondaryContact().getReportingId().getEmail();
						String recruiterMail = candidate.getUser().getEmail();

						Time time = new Time(interviewDetails.getInterviewTime().getTime());
						LocalTime lTime = time.toLocalTime();
						int hour = lTime.getHour();
						int min = lTime.getMinute();
						String interviewTime = null;

						if (min == 0) {
							interviewTime = hour + " : " + min + "0";
						} else {
							interviewTime = hour + " : " + min;
						}

						
						 //* ----------------------Candidate Mail Sending--------------------------
						 

						String messageHead1 = "Dear <i><B>" + "  " + candidate.getFirstName() + " "
								+ candidate.getLastName() + "</b></i></B><br><br>" + " Your   "
								+ interviewDetails.getNameOfRound() + " round interview is scheduled  <br>";

						String table = "<div>" + "<table border = 1>"
								+ "<tr><th  colspan=\"2\"  style = \" background-color: #ffb84d; \">Interview Details</th></tr>"
								+ "<tbody>" + "<tr><td> <b>Interview Type : </b></td>" + "<td>"
								+ interviewDetails.getInterviewType().getModeofInterview() + "</td></tr>"
								+ "<tr><td><b>Interview Date : </b></td>" + "<td>"
								+ new java.sql.Date(interviewDetails.getInterviewDate().getTime()).toLocalDate()
								+ "</td></tr>" + "<tr><td><b>Interview Time : </b></td><td>" + interviewTime
								+ "</td></tr>" + "<tr><td><b>Interview Location : </b></td><td>"
								+ interviewDetails.getInterviewLocation() + "</td></tr>"
								+ "<tr><td><b>client name : </b></td><td>" + client.getClientName()
								+ "</td></tr><tr><td>" + "<b>Venue Details : </b></td><td>"
								+ interviewDetails.getAddress() + "</td><tr>" + "</tbody></table></div>";

						String messageFoot = "<br><b>Thanks & Regards</b> <br>" + "<img src= \"cid:image\" alt="
								+ " Logo Not Available " + " width=" + "160" + " " + "height=" + " " + "80>";

						String message = messageHead1 + table + messageFoot;
						emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");
						emailEntity.setMessageBody(message);
						
						emailEntity.setTo(candidate.getEmail());
						StringBuffer sb = new StringBuffer();

						StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n"
								+ "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" + "VERSION:2.0\n"
								+ "METHOD:REQUEST\n" + "BEGIN:VEVENT\n"
								+ "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+candidate.getEmail()+" ORGANIZER:MAILTO:"+recruiterMail+" \n"
								+ " DTSTART:"+new java.sql.Date(interviewDetails.getInterviewDate().getTime()).toLocalDate()+"DTEND:20051208T060000Z\n" + "LOCATION:Conference room\n"
								+ "TRANSP:OPAQUE\n" + "SEQUENCE:0\n"
								+ "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n"
								+ " 000004377FE5C37984842BF9440448399EB02\n" + "DTSTAMP:"+new java.sql.Date(interviewDetails.getInterviewDate().getTime()).toLocalDate()+"CATEGORIES:Meeting\n"
								+ "DESCRIPTION:This the description of the meeting.\n\n" + "SUMMARY:|| Candidate : "+ candidate.getFirstName() +  candidate.getLastName() + " || Customer : " + client.getClientName()
								+ "PRIORITY:5\n" + "CLASS:PUBLIC\n" + "BEGIN:VALARM\n" + "TRIGGER:PT1440M\n" + "ACTION:DISPLAY\n"
								+ "DESCRIPTION:Reminder\n" + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR");
						emailEntity.setCalenderBody(buffer.toString());
						mailSender.sendMail(emailEntity);

						
						// * ----------------------Official Mail Sending--------------------------
						 

						String messageHead2 = "<i><b>Please consider below details of interview : </b></i><br><br>"
								+ "<b>Candidate Name : </b>" + "  " + candidate.getFirstName() + " "
								+ candidate.getLastName() + "<br>" + " <b>Level :</b>   "
								+ interviewDetails.getNameOfRound() + "<br><br>";

						String officialMessage = messageHead2 + table + messageFoot;

						emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");
						emailEntity.setMessageBody(officialMessage);
						emailEntity.setMessagesubject("Interview  Schedule || Candidate : " + candidate.getFirstName()
								+ " " + candidate.getLastName() + " || Customer : " + client.getClientName());
						emailEntity.setTo(managerMail + "," + primaryMail +  "," + leadMail + "," + recruiterMail);

						// Official Mail Sending
						mailSender.sendMail(emailEntity);

						// Sending WhatsApp Message
						MessageEntity msgEntity = ReadPropertiesFile.readMessageConfig();
						String msgText = "Dear  " + candidate.getFirstName() + " " + candidate.getLastName()
								+ ", your interview with " + client.getClientName() + " Date  : "  
								+   new java.sql.Date(interviewDetails.getInterviewDate().getTime()).toLocalDate()  +"Time : "+ interviewTime
								+ " is scheduled. Check your email for more details. please reply to confirm";

						msgEntity.setMsgText(msgText);
						msgEntity.setToNumber(candidate.getMobile());
						WhatsAppMessageSender.sendMessage(msgEntity);

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return new Response(ExceptionMessage.StatusSuccess, interviewDetails);
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update/{id}")
	public Response update(@PathParam("id") Long id, InterviewDetails interviewDetails) {

		Response response = new Response(ExceptionMessage.DataIsNotSaved);
		if (null != interviewDetails) {
			interviewDetails.setId(id);
			InterviewDetails interviewDetails1=	this.interviewDetailsDao.save(interviewDetails);
			processDao.updateCandiate(interviewDetails1.getCandidate().getId(), interviewDetails1.getRequirement().getId(), interviewDetails1.getUserId());
			response = new Response(ExceptionMessage.StatusSuccess, interviewDetails);
		}
		return response;
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/emailCalender")
	public Response send() throws Exception {

		Response response = new Response();
		
		
		String sql = "select i.interviewTime,i.interviewLocation,i.address,i.nameOfRound,c.email as candidateEmail,u.email as recuriterEamil  from interviewdetails i\r\n" + 
				"join candidate c on c.id = i.candidate_id\r\n" + 
				"join candidatemapping cm on i.userId = cm.mappedUser_id\r\n" + 
				"join user u on u.id = cm.mappedUser_id";

		try {

			String from = "rpo.info@ojas-it.com";
			String to = "mahichowdary540@gmail.com";
			Properties prop = new Properties();

			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.host", "outlook.office365.com");
			prop.put("mail.smtp.port", "587");

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("rpo.info@ojas-it.com", "Ojas1525");
				}
			});
			// Define message
			MimeMessage message = new MimeMessage(session);
			message.addHeaderLine("method=REQUEST");
			message.addHeaderLine("charset=UTF-8");
			message.addHeaderLine("component=VEVENT");

			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Outlook Meeting Request Using JavaMail");

			StringBuffer sb = new StringBuffer();

			StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n"
					+ "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" + "VERSION:2.0\n"
					+ "METHOD:REQUEST\n" + "BEGIN:VEVENT\n"
					+ "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:xx@xx.com\n" + "ORGANIZER:MAILTO:@xx.com\n"
					+ "DTSTART:20051208T053000Z\n" + "DTEND:20051208T060000Z\n" + "LOCATION:Conference room\n"
					+ "TRANSP:OPAQUE\n" + "SEQUENCE:0\n"
					+ "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n"
					+ " 000004377FE5C37984842BF9440448399EB02\n" + "DTSTAMP:20051206T120102Z\n" + "CATEGORIES:Meeting\n"
					+ "DESCRIPTION:This the description of the meeting.\n\n" + "SUMMARY:Test meeting request\n"
					+ "PRIORITY:5\n" + "CLASS:PUBLIC\n" + "BEGIN:VALARM\n" + "TRIGGER:PT1440M\n" + "ACTION:DISPLAY\n"
					+ "DESCRIPTION:Reminder\n" + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
			messageBodyPart.setHeader("Content-ID", "calendar_message");
			messageBodyPart
					.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very
																													// important

			// Create a Multipart
			Multipart multipart = new MimeMultipart();

			// Add part one
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			message.setContent(multipart);

			// send message
			Transport.send(message);
			response.setErrorCode("200");
			response.setRes("Mail Calender Event Sent Successfully...");
			response.setResult(new ArrayList());
		} catch (MessagingException me) {
			me.printStackTrace();

			response.setErrorCode("409");
			response.setRes("Uanle To Send Mail Calender Event");
			response.setResult(new ArrayList());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
	
	
	
	
}
