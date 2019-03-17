package com.planx.advertise.response;

public class RestResponseBody<T extends Object> {
	
	private String code;
	private String message;
	private RestStatus status;
	private T data;

	public RestResponseBody(RestStatus status) {
		this.status = status;
	}

	public RestResponseBody(T data, RestStatus status) {
		this.data = data;
		this.status = status;
	}
	
	public String getCode() {
		return code;
	}

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public RestStatus getStatus() {
		return status;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(RestStatus status) {
		this.status = status;
	}
}
