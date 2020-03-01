package com.seidli.javaspringsecurityjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private static final String SECRET_KEY = "secret"; //my super secret private key

    private static final Integer DEFAULT_JWT_LIFETIME = 10 * 60; //jwt's lifetime expressed in minutes, currently 10hs
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails, Integer lifetimeMinutes) {
        Map<String, Object> claims = new HashMap<>(); //payload's attributes
        return createToken(claims, userDetails.getUsername(), lifetimeMinutes);
    }
    
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails,DEFAULT_JWT_LIFETIME);
    }

    private String createToken(Map<String, Object> claims, String subject, Integer lifetimeMinutes) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + lifetimeMinutes * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Returns <b>True</b> if the userName held inside <i>userDetails</i> is the same as the one encripted inside the <i>token</i>
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
    	if(userDetails==null || token==null || token.trim().isEmpty()) 
    		return false;
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}