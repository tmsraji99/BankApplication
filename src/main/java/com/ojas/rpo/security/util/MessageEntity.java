package com.ojas.rpo.security.util;

public class MessageEntity {
	
	private String messageTransfer;
	private String token;
	private String fromNumber;
	private String toNumber;
	private String msgPrefix;
	private String msgText;
	private Long msgId;
	private String apiUrl;
	
	public String getMessageTransfer() {
		return messageTransfer;
	}
	public void setMessageTransfer(String messageTransfer) {
		this.messageTransfer = messageTransfer;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFromNumber() {
		return fromNumber;
	}
	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}
	public String getToNumber() {
		return toNumber;
	}
	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	public String getMsgPrefix() {
		return msgPrefix;
	}
	public void setMsgPrefix(String msgPrefix) {
		this.msgPrefix = msgPrefix;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
}
