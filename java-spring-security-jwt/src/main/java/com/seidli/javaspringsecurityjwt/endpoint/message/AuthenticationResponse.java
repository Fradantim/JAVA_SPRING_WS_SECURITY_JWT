package com.seidli.javaspringsecurityjwt.endpoint.message;

public class AuthenticationResponse {

	private String jwt;
	
	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
	
	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt=jwt;		
	}
}
