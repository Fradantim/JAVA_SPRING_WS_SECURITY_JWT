package com.seidli.javaspringsecurityjwt.endpoint.message;

public class HelloResponse {

	private String response;
	
	public HelloResponse(String response) {
		this.setResponse(response);
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
