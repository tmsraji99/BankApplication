package com.ojas.rpo.security.entity;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;

public class Response {
	private ExceptionMessage status;
	private String errorCode;
	private String errorMessage;
	private String exceptionType;
	private Object result;
	private String status2;
	private String res;
	private Integer totalPages;
	private Integer totalRecords;
	private String encodedString;

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Response(ExceptionMessage status, String res) {
		super();
		this.status = status;
		this.res = res;
	}

	private HttpStatus conflict;

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public Response() {
		this.status = ExceptionMessage.StatusSuccess;
	}

	public ExceptionMessage getStatus() {
		return status;
	}

	public void setStatus(ExceptionMessage status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setExceptionType(String exceptionType) {
		this.errorMessage = exceptionType;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setErrorException(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {

		this.result = result;
	}

	public Response(ExceptionMessage status, Object result) {
		super();
		this.status = status;
		this.result = result;

	}

	public Response(ExceptionMessage status) {
		super();
		this.status = status;

	}

	public Response(Object result, ExceptionMessage status) {
		this.status = status;
		this.result = result;
	}

	public Response(ExceptionMessage status, String errorCode, String errorMessage, String exceptionType) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.exceptionType = exceptionType;
	}

	public Response(ExceptionMessage status, String errorCode, String errorMessage) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;

	}
	
	public Response(ExceptionMessage status, Object result, String encodedString) {
		super();
		this.status = status;
		this.result = result;
		this.encodedString = encodedString;
	}

	public Response(Object result) throws JsonGenerationException, JsonMappingException, IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();

		this.status = ExceptionMessage.StatusSuccess;
		try {

			this.result = mapper.writeValueAsString(result);
			JSONParser parser = new JSONParser();
			try {
				this.result = parser.parse((String) this.result);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public Response(String status2, HttpStatus conflict, String res) {
		this.status2 = status2;
		this.res = res;
		this.conflict = conflict;

	}

	public HttpStatus getConflict() {
		return conflict;
	}

	public void setConflict(HttpStatus conflict) {
		this.conflict = conflict;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getEncodedString() {
		return encodedString;
	}

	public void setEncodedString(String encodedString) {
		this.encodedString = encodedString;
	}

}
