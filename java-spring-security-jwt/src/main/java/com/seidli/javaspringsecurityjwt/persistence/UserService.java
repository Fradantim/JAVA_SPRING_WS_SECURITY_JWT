package com.seidli.javaspringsecurityjwt.persistence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.seidli.javaspringsecurityjwt.model.User;

@Service
public class UserService {

	public static Map<String, User> usersMap = new HashMap<String, User>();
	
	static {
		List<User> users = Arrays.asList(
				new User("Barbara", "barbara", "Barbara Cerezo"),
				new User("Franco", "franco", "Franco Timpone"),
				new User("Exequiel", "exequiel", "Exequiel Moveva"),
				new User("Sergio", "sergio", "Sergio Gonzalez")			
				);
		
		for(User user: users) {
			usersMap.put(user.getUsername().toUpperCase(), user);
		}
	}
	
	public User findByUserName(String username) {
		return usersMap.get(username.toUpperCase());
	}
}
