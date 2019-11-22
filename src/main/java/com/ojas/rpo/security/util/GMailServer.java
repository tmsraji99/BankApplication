package com.ojas.rpo.security.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ojas.rpo.security.entity.Candidate;

public class GMailServer extends javax.mail.Authenticator {
	private String mailhost = "smtp.gmail.com";; // "smtp.mail.yahoo.com";
	private String user;
	private String password;
	private Session session;
	public GMailServer(EmailEntity emailEntity) {
		this.user = emailEntity.getFrom();
		this.password = emailEntity.getPassword();

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", mailhost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		session = Session.getDefaultInstance(props, this);
	}
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}
	public synchronized void sendMail(EmailEntity emailEntity) {
		MimeMessage message = null;
		DataHandler handler=null;
		try {
			message = new MimeMessage(session);
			handler= new DataHandler(new ByteArrayDataSource(emailEntity.getMessageBody().getBytes(), "text/html"));
			message.setSender(new InternetAddress(emailEntity.getFrom()));
			message.setSubject(emailEntity.getMessagesubject());
			message.setDataHandler(handler);

			if (emailEntity.getTo().indexOf(',') > 0){
			
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailEntity.getTo()));
			}
			else{
			
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailEntity.getTo()));
			}

			/*if(candidate.getCandidateStatus().equalsIgnoreCase("Candidate OfferRejected")&&candidate.getCandidateStatus().equalsIgnoreCase("Candidate OfferAccepted")&&candidate.getCandidateStatus().equalsIgnoreCase("Offer Released"))
				message.setRecipient(Message.RecipientType.CC, new InternetAddress(emailEntity.getCc()));
			*/
			
			
			//Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {if(message!=null){	message=null;}if(handler!=null){	handler=null;}}
		
	}
	
	public synchronized void sendMail1(EmailEntity emailEntity,InternetAddress[] addr) {
		MimeMessage message = null;
		DataHandler handler=null;
		try {
			message = new MimeMessage(session);
			handler= new DataHandler(new ByteArrayDataSource(emailEntity.getMessageBody().getBytes(), "text/html"));
			message.setSender(new InternetAddress(emailEntity.getFrom()));
			message.setSubject(emailEntity.getMessagesubject());
			message.setDataHandler(handler);
			message.addRecipients(Message.RecipientType.CC, addr);
			if (emailEntity.getTo().indexOf(',') > 0){
			
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailEntity.getTo()));
			}
			else{
			
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailEntity.getTo()));
			}

			/*if(candidate.getCandidateStatus().equalsIgnoreCase("Candidate OfferRejected")&&candidate.getCandidateStatus().equalsIgnoreCase("Candidate OfferAccepted")&&candidate.getCandidateStatus().equalsIgnoreCase("Offer Released"))
				message.setRecipient(Message.RecipientType.CC, new InternetAddress(emailEntity.getCc()));
			*/
			
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {if(message!=null){	message=null;}if(handler!=null){	handler=null;}}
		
	}

	public class ByteArrayDataSource implements DataSource {
		private byte[] data;
		private String type;

		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getContentType() {
			if (type == null)
				return "application/octet-stream";
			else
				return type;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		public String getName() {
			return "ByteArrayDataSource";
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("Not Supported");
		}
	}
}
