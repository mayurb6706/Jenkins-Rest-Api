package com.cwm.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.product.entity.UserRequest;
import com.cwm.product.entity.UserResponse;
import com.cwm.product.utils.JwtUtils;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Login Api", description = "User login api to generate user auth token.")
public class AuthController {

	@Autowired
	private JwtUtils utils;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/login")
	@ResponseStatus(value = HttpStatus.OK)
	public UserResponse login(@RequestBody UserRequest userRequest) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		String token= utils.gernerateToken(userRequest.getUsername());
		UserResponse response= UserResponse.builder().token(token).message("Success").status(HttpStatus.OK).build();
		return  response;
	}
}
