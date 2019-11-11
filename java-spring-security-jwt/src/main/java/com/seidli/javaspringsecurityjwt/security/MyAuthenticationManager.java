package com.seidli.javaspringsecurityjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.seidli.javaspringsecurityjwt.model.User;
import com.seidli.javaspringsecurityjwt.persistence.UserService;

@Service
public class MyAuthenticationManager implements AuthenticationManager{

	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = userService.findByUserName(authentication.getPrincipal().toString());
		
		if(user == null || !user.getPassword().equals(authentication.getCredentials().toString())) {
			throw new BadCredentialsException("username o password incorrectos.");
		}
		return null;
	}

}
