package com.ojas.rpo.security.entity;

public enum ExceptionMessage {
	
	DataIsEmpty("message_DataIsEmpty"),
	DataIsNotSaved("message_DataIsNotSaved"),
	TokenIsEmpty("message_TokenIsEmpty"),
	TokenIsInvalid("message_TokenIsInvalid"),
	TokenIsExpire("message_TokenIsExpire"),
	ServerTimeOut("message_ServerTimeOut"),
    UnAuthorizationUser("message_ UnAuthorizationUser"),
    UnRegistrationUser("message_ UnRegistrationUser"),
	StatusSuccess("message_Success"),
	DuplicateRecord("message_DuplicateRecord"),
	OK("200"),
	Bad_Request("400"),
	Accepted("202"),
	Unauthorized ("401"),
	Created ("201"),
	Not_Found ("404"),
	ExcepcetdDataNotAvilable("message_ExcepcetdDataNotAvilable"),
	Exception("500");
	
    private String exceptionMes;

	public String getExceptionMes() {
		return exceptionMes;
	}

	private ExceptionMessage(String exceptionMes) {
		this.exceptionMes = exceptionMes;
	}

}
