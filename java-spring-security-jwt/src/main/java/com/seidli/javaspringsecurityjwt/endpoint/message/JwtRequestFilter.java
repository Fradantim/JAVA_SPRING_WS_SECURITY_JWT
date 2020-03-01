package com.seidli.javaspringsecurityjwt.endpoint.message;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.seidli.javaspringsecurityjwt.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	private static final String AUTHORIZATION_HEADER="Authorization";
	
	private static final String AUTHORIZATION_KEY="BEARER ";
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		//is someone already authenticated?
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			
			//check if the request has an  authorization header to further check if an 
			final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
			
			if(authorizationHeader != null && authorizationHeader.toUpperCase().startsWith(AUTHORIZATION_KEY)) {
				String jwt = authorizationHeader.substring(AUTHORIZATION_KEY.length());
				String username= jwtUtil.extractUsername(jwt);
				
				if (username != null) {
		            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

		            if (jwtUtil.validateToken(jwt, userDetails)) {
		            	//check if the token is not expired and if the specified user inside is the same I have in my "database" 
		                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		                //if validations where successful 
		                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		                //add a new generated jwt to the response to allow client to keep "authenticated"
		                response.addHeader("new-jwt", jwtUtil.generateToken(userDetails));
		            }
		        }
			}
		}

		filterChain.doFilter(request, response);
	}
}
