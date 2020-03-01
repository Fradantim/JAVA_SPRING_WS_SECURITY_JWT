package com.seidli.javaspringsecurityjwt.model;

import java.util.ArrayList;

public class User {

	private String username;
	private String password;
	private String completeName;
	
	public User(String username, String password, String completeName) {
		this.username = username;
		this.password = password;
		this.completeName = completeName;
	}
	
	public User(String username) {
		this.username= username;
	}
	
	public User() { }
	
	public org.springframework.security.core.userdetails.User toSpringSecUser(){
		return new org.springframework.security.core.userdetails.User(username, password, new ArrayList<>());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.toUpperCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.toUpperCase().equals(other.username.toUpperCase()))
			return false;
		return true;
	}	
}
