package com.ojas.recruitex.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

	private T t;
	private List<String> error;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public List<String> getError() {
		if (this.error == null)
			this.error = new ArrayList<String>();
		return this.error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

}
