package com.seidli.javaspringsecurityjwt.endpoint;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seidli.javaspringsecurityjwt.endpoint.message.HelloResponse;
import com.seidli.javaspringsecurityjwt.persistence.UserService;

@RestController
public class HelloEndpoint {

	@Autowired
	private UserService userService;
	
	@RequestMapping({"/hello"})
	public HelloResponse hello(Principal principal) {
		return new HelloResponse("Wellcome "+userService.findByUserName(principal.getName()).getCompleteName()+"!");
	}

}
