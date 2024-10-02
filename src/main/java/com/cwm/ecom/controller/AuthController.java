package com.cwm.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.ecom.entity.UserRequest;
import com.cwm.ecom.entity.UserResponse;
import com.cwm.ecom.utils.JwtUtils;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Login Api", description = "User login api to generate user auth token.")
public class AuthController {

	
	private final AuthenticationManager authenticationManager;
	
	private final JwtUtils utils;
	
	@Autowired
	public AuthController(AuthenticationManager manager, JwtUtils utils) {
		this.authenticationManager=manager;
		this.utils= utils;
	}

	@PostMapping("/login")
	public UserResponse login(@RequestBody UserRequest userRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		String token = utils.gernerateToken(userRequest.getUsername());
		System.out.println("Authentication " + authentication);
		UserResponse response = UserResponse.builder().token(token).message("Success").status(HttpStatus.OK).build();
		return response;

	}
}
