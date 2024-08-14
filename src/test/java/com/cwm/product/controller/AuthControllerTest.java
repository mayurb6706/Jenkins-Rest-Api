package com.cwm.product.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cwm.product.entity.UserRequest;
import com.cwm.product.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private JwtUtils jwtUtils;
	
	@Autowired
	private ObjectMapper mapper;
	
	private UserRequest request;
	@BeforeEach
	void setUp() throws Exception {
		
		request= UserRequest.builder()
				.username("username")
				.password("password")
				.build();
	}

	@Test
	void testLogin() throws Exception {
		
		String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJpc3MiOiJNYXl1ciIsImlhdCI6MTcyMzYxNDc0OSwiZXhwIjoxNzIzNjQ0NzQ5fQ.BfBc1bFnJcT3UWCPzDNTnbktOuXZqDnAUIAmqPFC184Z-yWi10JlTN8FSsoIBhimWa1VCu3NTPMyRnKbmRLnPA";
		BDDMockito.given(jwtUtils.gernerateToke(request.getUsername())).willReturn(token);
		
		ResultActions resultActions= mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)));
		
		resultActions.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.token",is(token)))
		.andExpect(jsonPath("$.message", is("Success")));
		
		
	}

}
