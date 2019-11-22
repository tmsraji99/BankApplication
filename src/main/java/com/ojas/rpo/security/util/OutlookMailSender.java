package com.ojas.rpo.security.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.TaskExecutor;

public class OutlookMailSender extends Authenticator {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private TaskExecutor taskExecutor;

	Properties props = new Properties();

	private Properties getMailProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");
		return props;
	}

	public OutlookMailSender() {
		super();
	}

	public void sendMail(final EmailEntity emailEntity) {

		taskExecutor.execute(new Runnable() {
			public void run() {
				try {

					Session session = Session.getInstance(getMailProperties(), new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(emailEntity.getFrom(), emailEntity.getPassword());
						}
					});

					/*
					 * Message message = new MimeMessage(session);
					 * message.setFrom(new
					 * InternetAddress(emailEntity.getFrom()));
					 * message.setRecipients(Message.RecipientType.TO,
					 * InternetAddress.parse(emailEntity.getTo())); DataHandler
					 * handler= new DataHandler(new
					 * ByteArrayDataSource(emailEntity.getMessageBody().getBytes
					 * (), "text/html"));
					 * message.setSubject(emailEntity.getMessagesubject());
					 * message.setDataHandler(handler);
					 */

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(emailEntity.getFrom()));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailEntity.getTo()));
					if (null != emailEntity.getCc()) {
						message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailEntity.getCc()));
					}
					message.setSubject(emailEntity.getMessagesubject());

					MimeMultipart multipart = new MimeMultipart("related");

					BodyPart messageBodyPart = new MimeBodyPart();
					String htmlText = emailEntity.getMessageBody();
					messageBodyPart.setContent(htmlText, "text/html");
					multipart.addBodyPart(messageBodyPart);

					MimeBodyPart imagePart = new MimeBodyPart();
					try {
						DataHandler handler = new DataHandler(new URL(emailEntity.getLogoImagePath()));
						imagePart.setDataHandler(handler);

						/*if (emailEntity.getCalenderBody() != null) {
							messageBodyPart.setDataHandler(new DataHandler(
									new ByteArrayDataSource(emailEntity.getCalenderBody(), "text/calendar")));
						}*/
					} catch (Exception e) {
						e.printStackTrace();
					}

					imagePart.setHeader("Content-ID", "<image>");
					imagePart.setDisposition(Part.INLINE);
					multipart.addBodyPart(imagePart);

					message.setContent(multipart);
					// very

					Transport.send(message);

					System.out.println("Done");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/////////////////

	/*
	 * public static void send() throws Exception {
	 * 
	 * try { String from = ".com"; String to = "xx@xx.com"; Properties prop =
	 * new Properties(); prop.put("mail.smtp.host", "mailhost");
	 * 
	 * Session session = Session.getDefaultInstance(prop, null); // Define
	 * message MimeMessage message = new MimeMessage(session);
	 * message.addHeaderLine("method=REQUEST");
	 * message.addHeaderLine("charset=UTF-8");
	 * message.addHeaderLine("component=VEVENT");
	 * 
	 * message.setFrom(new InternetAddress(from));
	 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	 * message.setSubject("Outlook Meeting Request Using JavaMail");
	 * 
	 * StringBuffer sb = new StringBuffer();
	 * 
	 * StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
	 * "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
	 * "VERSION:2.0\n" + "METHOD:REQUEST\n" + "BEGIN:VEVENT\n" +
	 * "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:xx@xx.com\n" +
	 * "ORGANIZER:MAILTO:xx@xx.com\n" + "DTSTART:20051208T053000Z\n" +
	 * "DTEND:20051208T060000Z\n" + "LOCATION:Conference room\n" +
	 * "TRANSP:OPAQUE\n" + "SEQUENCE:0\n" +
	 * "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n"
	 * + " 000004377FE5C37984842BF9440448399EB02\n" +
	 * "DTSTAMP:20051206T120102Z\n" + "CATEGORIES:Meeting\n" +
	 * "DESCRIPTION:This the description of the meeting.\n\n" +
	 * "SUMMARY:Test meeting request\n" + "PRIORITY:5\n" + "CLASS:PUBLIC\n" +
	 * "BEGIN:VALARM\n" + "TRIGGER:PT1440M\n" + "ACTION:DISPLAY\n" +
	 * "DESCRIPTION:Reminder\n" + "END:VALARM\n" + "END:VEVENT\n" +
	 * "END:VCALENDAR");
	 * 
	 * // Create the message part BodyPart messageBodyPart = new MimeBodyPart();
	 * 
	 * // Fill the message messageBodyPart.setHeader("Content-Class",
	 * "urn:content-  classes:calendarmessage");
	 * messageBodyPart.setHeader("Content-ID", "calendar_message");
	 * messageBodyPart .setDataHandler(new DataHandler(new
	 * ByteArrayDataSource(buffer.toString(), "text/calendar")));// very //
	 * important
	 * 
	 * // Create a Multipart Multipart multipart = new MimeMultipart();
	 * 
	 * // Add part one multipart.addBodyPart(messageBodyPart);
	 * 
	 * // Put parts in message message.setContent(multipart);
	 * 
	 * // send message Transport.send(message); } catch (MessagingException me)
	 * { me.printStackTrace(); } catch (Exception ex) { ex.printStackTrace(); }
	 * }
	 */

	public class ByteArrayDataSource implements DataSource {
		private byte[] data;
		private String type;

		private String string;
		private String type2;

		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}

		public String getType2() {
			return type2;
		}

		public void setType2(String type2) {
			this.type2 = type2;
		}

		public String getType() {
			return type;
		}

		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}

		public ByteArrayDataSource(String string, String type2) {
			super();
			this.string = string;
			this.type2 = type2;
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
