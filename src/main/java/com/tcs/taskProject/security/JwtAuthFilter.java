package com.tcs.taskProject.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthFilter  extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	CustomUserDeatailsService customUserDeatailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token=getToken(request);
		if(StringUtils.hasText(token)&& jwtTokenProvider.validateToken(token)) {
			
		String email=	jwtTokenProvider.getEmailFromToken(token);
			
		UserDetails userDetails=	customUserDeatailsService.loadUserByUsername(email);
		
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
			
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
		
		
		
	}
		
		//get the token from header
		//check the token valid or not
		//load the user and setAuthenication
		
	
	
		public String getToken(HttpServletRequest request) {
			
			String token=request.getHeader("Authorization");
			
			if(StringUtils.hasText(token)&& token.startsWith("Bearer ")) {
				
			return token.substring(7,token.length());
			}
			
			return null;
		}
		
		
		
		
	

}
