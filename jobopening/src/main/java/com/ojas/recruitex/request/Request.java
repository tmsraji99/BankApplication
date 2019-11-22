package com.ojas.recruitex.request;

public class Request<T> {

	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Request(Object object) {
		super();
		this.object = object;
	}

	public Request() {
		super();
	}

}
