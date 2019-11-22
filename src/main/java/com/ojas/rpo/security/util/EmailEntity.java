package com.ojas.rpo.security.util;

public class EmailEntity {
	private String from;
	private String password;
	private String to;
	private String cc;
	private String bcc;
	private String messageBody;
	private String messagesubject;
	private String logoImagePath;
	
	private String calenderBody;

	public String getCalenderBody() {
		return calenderBody;
	}

	public void setCalenderBody(String calenderBody) {
		this.calenderBody = calenderBody;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getMessagesubject() {
		return messagesubject;
	}

	public void setMessagesubject(String messagesubject) {
		this.messagesubject = messagesubject;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getLogoImagePath() {
		return logoImagePath;
	}

	public void setLogoImagePath(String logoImagePath) {
		this.logoImagePath = logoImagePath;
	}
	
}
