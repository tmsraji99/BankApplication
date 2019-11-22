package com.ojas.rpo.security.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.InterviewFeedback;
import com.ojas.rpo.security.entity.Processtype;

public class MailGenaration {

	public boolean sendSpecificMail(Candidate candidate,String status) {

		String toAddress = "";
		String mailBody = "";
		String mailsubject = "";
		if (status.equalsIgnoreCase("Aproved By AM")) {
			mailsubject = "Aproved By AM";
			toAddress = candidate.getUser().getEmail();
			mailBody = candidate.getFirstName() + " " + candidate.getLastName() + " Profile has been Approved";
			sendMail(toAddress, mailBody, mailsubject);
			return true;
		} else if (status.equalsIgnoreCase("AM Rejected")) {
			mailsubject = "Rejected by AM";
			toAddress = candidate.getUser().getEmail();
			mailBody = candidate.getFirstName() + " " + candidate.getLastName() + " Profile has been Rejected";
			sendMail(toAddress, mailBody, mailsubject);

			return true;
		}

		else if (status.equalsIgnoreCase("ReviewPending")) {
			mailsubject = "ReviewPending";
			toAddress = candidate.getUser().getEmail();
			mailBody = candidate.getFirstName() + " " + candidate.getLastName() + " Resume Screening was Pending";
			sendMail(toAddress, mailBody, mailsubject);

			return true;
		}

		else if (status.equalsIgnoreCase("Submitted to Customer")) {
			mailsubject = "Submitted to Customer";
			toAddress = candidate.getUser().getEmail();
			mailBody = candidate.getFirstName() + " " + candidate.getLastName()
					+ " Resume was sucessfully submitted to Customer";
			sendMail(toAddress, mailBody, mailsubject);

			return true;
		}

		else if (status.equalsIgnoreCase("Customer Shortlisted")) {
			mailsubject = "Customer Shortlisted";
			toAddress = candidate.getUser().getEmail();
			mailBody = candidate.getFirstName() + " " + candidate.getLastName() + " Resume was shortlisted by Customer";
			sendMail(toAddress, mailBody, mailsubject);

			return true;
		}

		else if (status.equalsIgnoreCase("Customer Rejected")) {
			mailsubject = "Customer Rejected";
			toAddress = candidate.getUser().getEmail();
			mailBody = candidate.getFirstName() + " " + candidate.getLastName() + " Resume was Rejected by Customer";

			sendMail(toAddress, mailBody, mailsubject);

			return true;
		}

		else if (status.equalsIgnoreCase("Offer Released")) {
			
			HashMap<String, String> mailList = null;
			InternetAddress[] address = null;
			mailsubject = "Offer Released";
			Iterator<String> itr = null;
			Set<String> addr = null;
			int count = 0;
			
			
			toAddress = candidate.getUser().getEmail();
			mailBody = "Name of the candidate:"+candidate.getFirstName() + " " + candidate.getLastName()+ "</b><br>" 
			+"canidate Status:"+ " Offer Released";
			mailList = ReadPropertiesFile.getMailAddress();
			address = new InternetAddress[mailList.size()];
			addr = mailList.keySet();
			for (String addrName : addr) {
				try {
					address[count] = new InternetAddress(mailList.get(addrName));
					++count;
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}
			sendMail(toAddress, mailBody, mailsubject, address);

			
			
			
			toAddress = candidate.getEmail();
			mailBody = candidate.getFirstName() + " " + candidate.getLastName() + " Offer Released";
			sendMail(toAddress, mailBody, mailsubject, address);

			

			return true;
		}

		else if (status.equalsIgnoreCase("Candidate OfferAccepted")) {
			HashMap<String, String> mailList = null;
			InternetAddress[] address = null;
			mailsubject = "Candidate OfferAccepted";
			Iterator<String> itr = null;
			Set<String> addr = null;
			int count = 0;

			toAddress = candidate.getUser().getEmail();
			mailBody = "Name of the candidate:"+candidate.getFirstName() + " " + candidate.getLastName()+ "</b><br>"
			+"canidate Status:"+ " Candidate OfferAccepted"
			+ "</b><br>" + "Date Of Joing:" + candidate.getDoj();
			mailList = ReadPropertiesFile.getMailAddress();
			address = new InternetAddress[mailList.size()];
			addr = mailList.keySet();
			for (String addrName : addr) {
				try {
					address[count] = new InternetAddress(mailList.get(addrName));
					++count;
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}
			sendMail(toAddress, mailBody, mailsubject, address);
			return true;
		}

	/*	else if (status.equalsIgnoreCase("Candidate OfferRejected")) {
			HashMap<String, String> mailList = null;
			InternetAddress[] address = null;
			mailsubject = "Candidate OfferRejected";
			Iterator<String> itr = null;
			Set<String> addr = null;
			int count = 0;

			toAddress = candidate.getUser().getEmail();
			mailBody = "Name of the candidate:"+candidate.getFirstName() + " " + candidate.getLastName()+ "</b><br>"
			+"canidate Status:"+ " Candidate OfferRejected"
			+ "</b><br>" + "Candidate Rejected Reason:" + candidate.getOffRejectedReasion();
			mailList = ReadPropertiesFile.getMailAddress();
			address = new InternetAddress[mailList.size()];
			addr = mailList.keySet();
			for (String addrName : addr) {
				try {
					address[count] = new InternetAddress(mailList.get(addrName));
					++count;
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}
			sendMail(toAddress, mailBody, mailsubject, address);
			return true;
		}*/

		return false;

	}

	public boolean sendSpecificMail(Processtype processtype) {

		String toAddress = "";
		String mailBody = "";
		String mailsubject = "";

		if (processtype.getTypeofinterview().equalsIgnoreCase("F2F")) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			mailsubject = "interview schedule";
			toAddress = processtype.getCandidateEmail();
			// mailBody=processtype.getCandidateiname()+"
			// "+candidate.getLastName()+ " Resume was Approved";
			mailBody = "<i>Dear, </i><B>" + processtype.getCandidateiname().toUpperCase() + "</B>" + "<br>" // add
																											// message
																											// body
					+ "Greetings from Ojas!!!" + "<br><br>" + "Company Name : <b>" + processtype.getCompanyname()
					+ "</b><br>" + "Contact Details : <br>" + processtype.getContactperson() + " - Ph:"
					+ processtype.getMobilenumber() + "<br><br><br>" + "Interview Type : "
					+ processtype.getTypeofinterview() + "<br>" + "Date & Time : "
					+ formatter.format(processtype.getDate()) + "  " + processtype.getTime() + "<br>"
					+ "<br>Address : <br><p>" + processtype.getAddress() + ",<br>" + processtype.getCity() + ",<br>"
					+ processtype.getState() + ",<br>" + processtype.getCountry() + ",<br>" + processtype.getPincode()
					+ ".<p>" + "<br>" + "<br><br><br><h6>This is automatic genarate Mail...</h6>";
			sendMail(toAddress, mailBody, mailsubject);
			return true;
		}

		else if (processtype.getTypeofinterview().equalsIgnoreCase("WebX")) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			mailsubject = "interview schedule";
			toAddress = processtype.getCandidateEmail();
			// mailBody=processtype.getCandidateiname()+"
			// "+candidate.getLastName()+ " Resume was Approved";
			mailBody = "<i>Dear, </i><B>" + processtype.getCandidateiname().toUpperCase() + "</B>" + "<br>" // add
																											// message
																											// body
					+ "Greetings from Ojas!!!" + "<br><br>" + "Company Name : <b>" + processtype.getCompanyname()
					+ "</b><br>" + "Contact Details : <br>" + processtype.getContactperson() + " - Ph:"
					+ processtype.getMobilenumber() + "<br><br><br>" + "Interview Type : "
					+ processtype.getTypeofinterview() + "<br>" + "Date & Time : "
					+ formatter.format(processtype.getDate()) + "  " + processtype.getTime() + "<br>" + "WebxID : "
					+ processtype.getWebxId() + ".<p>" + "<br>"
					+ "<br><br><br><h6>This is automatic genarate Mail...</h6>";
			sendMail(toAddress, mailBody, mailsubject);
			return true;
		}

		else if (processtype.getTypeofinterview().equalsIgnoreCase("Teliphonic")
				&& processtype.getTypeofinterview().equalsIgnoreCase("Skype")) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			mailsubject = "interview schedule";
			toAddress = processtype.getCandidateEmail();
			mailBody = "<i>Dear, </i><B>" + processtype.getCandidateiname().toUpperCase() + "</B>" + "<br>" // add
																											// message
																											// body
					+ "Greetings from Ojas!!!" + "<br><br>" + "Company Name : <b>" + processtype.getCompanyname()
					+ "</b><br>" + "Contact Details : <br>" + processtype.getContactperson() + " - Ph:"
					+ processtype.getMobilenumber() + "<br><br><br>" + "Interview Type : "
					+ processtype.getTypeofinterview() + "<br>" + "Date & Time : "
					+ formatter.format(processtype.getDate()) + "  " + formatter.format(processtype.getTime()) + "<br>"
					+ ".<p>" + "<br>" + "<br><br><br><h6>This is automatic genarate Mail...</h6>";
			sendMail(toAddress, mailBody, mailsubject);
			return true;
		}
		return false;

	}

	public boolean sendSpecificMail(InterviewFeedback feedback) {

		String toAddress = "";
		String mailBody = "";
		String mailsubject = "";
		if (true) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			mailsubject = "interview schedule";
			toAddress = feedback.getCandidateemail();
			// mailBody=processtype.getCandidateiname()+"
			// "+candidate.getLastName()+ " Resume was Approved";
			mailBody = "<i>Dear, </i><B>" + feedback.getCandidateiname().toUpperCase() + "</B>" + "<br>" // add
																											// message
																											// body
					+ "Greetings from Ojas!!!" + "<br><br>" + "Company Name : <b>" + feedback.getCompanyname()
					+ "</b><br>" + "Date & Time : " + formatter.format(feedback.getDate()) + "<br><br>"
					+ "clentfeedback:" + feedback.getClientFeedback() + "<br><br>" + "Internaldback:"
					+ feedback.getInternalFeedback() + "<br><br>" + "InterviewStatus:" + feedback.getInterviewStatus()
					+ "<br><br>" + "numberOfRounds:" + feedback.getNameOfRound() + "<br><br>";
			sendMail(toAddress, mailBody, mailsubject);
			return true;
		}

		return false;

	}

	private void sendMail(String toAddress, String mailbody, String mailsubject) {
		EmailEntity emailEntity = ReadPropertiesFile.readConfig();
		emailEntity.setMessagesubject(mailsubject);// add subject
		emailEntity.setTo(toAddress);// add to address
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		emailEntity.setMessageBody(mailbody);
		GMailServer sender = new GMailServer(emailEntity);// create gmail server
		sender.sendMail(emailEntity);// send mail
		System.out.println("Email Sent Succesfully...");

	}

	private void sendMail(String toAddress, String mailbody, String mailsubject, InternetAddress[] address) {
		EmailEntity emailEntity = ReadPropertiesFile.readConfig();
		emailEntity.setMessagesubject(mailsubject);// add subject
		emailEntity.setTo(toAddress);// add to address
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		emailEntity.setMessageBody(mailbody);
		GMailServer sender = new GMailServer(emailEntity);// create gmail server
		sender.sendMail1(emailEntity,address);// send mail
		System.out.println("Email Sent Succesfully...");

	}
}
