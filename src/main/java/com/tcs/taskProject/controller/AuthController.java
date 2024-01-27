package com.tcs.taskProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.taskProject.payLoad.JwtAuthResponse;
import com.tcs.taskProject.payLoad.LoginDto;
import com.tcs.taskProject.payLoad.UserDto;
import com.tcs.taskProject.security.JwtTokenProvider;
import com.tcs.taskProject.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	
	//store the user in database
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		
		
		return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
	}
		
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginDto loginDto){
			
	Authentication authentication =authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
	
	
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	String token=jwtTokenProvider.generateToken(authentication);
	
	
	
	return ResponseEntity.ok(new JwtAuthResponse(token));
		
		
	}
}