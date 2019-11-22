package com.ojas.rpo.security.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ReadPropertiesFile {
	public static EmailEntity readConfig() {
		EmailEntity emailEntity = new EmailEntity();
		System.out.println("setReadConfig");

		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties"));
			emailEntity.setFrom((String) properties.get("setFrom"));
			emailEntity.setPassword((String) properties.get("setPassword"));
			String ccMails = properties.get("CEO").toString()+","+properties.get("BDMLead").toString();
			//emailEntity.setCc(ccMails);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return emailEntity;
	}
	
	public static MessageEntity readMessageConfig() {
		MessageEntity messageEntity = new MessageEntity();
		System.out.println("setReadConfig");

		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("message.properties"));
			messageEntity.setFromNumber((String) properties.get("fromNumber"));
			messageEntity.setToken((String) properties.get("token"));
			messageEntity.setMsgPrefix((String) properties.get("msgPrefix"));
			messageEntity.setMessageTransfer((String) properties.get("messageTransfer"));
			messageEntity.setApiUrl((String) properties.get("apiUrl"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messageEntity;
	}

	public static HashMap<String, String> getMailAddress() {

		Properties properties = new Properties();
		HashMap<String, String> mailList = new HashMap<String, String>();
		String address[] = { "HR", "Finance", "CEO"};
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties"));
			for (String addr : address) {
				mailList.put(addr, properties.getProperty(addr));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mailList;
	}

}