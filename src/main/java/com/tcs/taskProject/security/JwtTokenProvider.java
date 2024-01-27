package com.tcs.taskProject.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tcs.taskProject.exceptionHandling.ApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	public String generateToken(Authentication authentication) {
		
		String email=authentication.getName();
		
		Date current= new Date();
		
		Date expire= new Date(current.getDate()+360000);
		
		
		String token=Jwts.builder()
				.setSubject(email)
				.setIssuedAt(current)
				.setExpiration(expire)
				.signWith(SignatureAlgorithm.HS256,"JWTSecurityKeyJWTSecurityKeyJWTSecurityKeyJWTSecurityKey")
				.compact();
		
		
		return token;
		
	}
	public String getEmailFromToken(String token) {
		
		
	Claims claim=  Jwts.parser().setSigningKey("JWTSecurityKeyJWTSecurityKeyJWTSecurityKeyJWTSecurityKey").parseClaimsJws(token).getBody();
	
	
	return claim.getSubject();
		
		
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parser().setSigningKey("JWTSecurityKeyJWTSecurityKeyJWTSecurityKeyJWTSecurityKey").parseClaimsJws(token);
		return true;	
		}
		catch(Exception e) {
			
			throw new ApiException("Token issue:"+e.getMessage());
			
		}
		
	}

}
