package com.ojas.rpo.security.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class WhatsAppMessageSender {

	public static ResponseEntity<String> sendMessage(MessageEntity msgEntity) {
		ResponseEntity<String> responseVal = null;
		MultiValueMap<String, String> variables = new LinkedMultiValueMap<String, String>();
		variables.add("token", msgEntity.getToken());
		variables.add("uid", msgEntity.getFromNumber());
		variables.add("to", 91 + msgEntity.getToNumber());

		long currentMilliseconds = new Date().getTime();

		String mseg = md5Java(currentMilliseconds + "");

		variables.add("custom_uid", msgEntity.getMsgPrefix() + "-" + mseg);
		variables.add("text", msgEntity.getMsgText());

		if (msgEntity.getMessageTransfer().equalsIgnoreCase("on")) {

			try {

				CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
						.build();
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				requestFactory.setHttpClient(httpClient);
				RestTemplate restTemplate = new RestTemplate(requestFactory);
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
				headers.add("user-agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
				HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(msgEntity.getApiUrl());
				builder.queryParams(variables);

				UriComponents uriComponents = builder.build().encode();
				responseVal = restTemplate.exchange(uriComponents.toUri(), HttpMethod.POST, requestEntity,
						String.class);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} else {
			responseVal = new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		return responseVal;
	}

	public static String md5Java(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			// converting byte array to Hexadecimal
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();

		} catch (UnsupportedEncodingException ex) {

		} catch (NoSuchAlgorithmException ex) {

		}
		return digest;

	}

}
