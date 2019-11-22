package com.ojas.rpo.security.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.ojas.rpo.security.entity.Candidate;

public class SendSMS {
	public static String sendSms() {
		try {
			// Construct data
			String user = "username=" + "jpraj@ojas-it.com";
			String hash = "&hash=" + "0e4f082b9cf1176f70f8971cca2f109612e0e961";
			String message = "&message=" + "This is your message";
			String sender = "&sender=" + "OJASIT";
			String numbers = "&numbers=" + "919441639306";
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
			String data = user + hash + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
	public static void main(String[] args) {
		System.out.println(sendSms());
		System.out.println("messgge send");
	}
}
