package com.cwm.ecom.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwm.ecom.entity.UserRequest;
import com.cwm.ecom.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

	@Mock
	JwtUtils utils;

	@MockBean
	AuthenticationManager authManager;

	@InjectMocks
	AuthController authController;

	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	private UserRequest request;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
		request = UserRequest.builder().username("username").password("password").build();

	}

	@Test
	public void testLogin() throws Exception {
		Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJpc3MiOiJNYXl1ciIsImlhdCI6MTcyMzc4ODU4NCwiZXhwIjoxNzIzODE4NTg0fQ.p8ISIV97Tw_LWjst6XJ5v0pfT9nRFLZ0DRyRlqxOLalu0YcufVWG7aoViysWEpt0v8TlLar0Lb5QVSCy5RDwGw";

		BDDMockito.given(authManager.authenticate(new UsernamePasswordAuthenticationToken("username", "password")))
				.willReturn(authentication);
		System.out.println(authentication);
		BDDMockito.given(utils.gernerateToken("username")).willReturn(token);

		ResultActions resultActions = mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)));

		resultActions.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.token").value(token));
		
	}
}
