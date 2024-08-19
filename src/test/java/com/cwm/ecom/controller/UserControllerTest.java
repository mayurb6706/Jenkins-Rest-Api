package com.cwm.ecom.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwm.ecom.model.Address;
import com.cwm.ecom.model.User;
import com.cwm.ecom.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserControllerTest {

	private final String BASE_URL = "/api/user";
	
	private MockMvc mockMvc;

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private UserController userController;
	
	private User user;


	@Autowired
	private ObjectMapper mapper=new ObjectMapper();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		Set<String> roles = new HashSet<>();
		roles.add("USER");

		Address address = Address.builder().id(1L).state("Maharashtra").street("Karvenager").city("Pune").country("INDIA").pin("11111")
				.build();

		 user = User.builder().id(1L).firstName("Mayur").lastName("Bhosale").email("mayur@test.com").contact("1234567890")
				.password("password").username("username").address(address).role(roles).build();
	}

	@Test
	public void testSaveUser() throws Exception {
		
		// Mock the behavior of userService
		BDDMockito.given(userService.saveUser(any(User.class))).willReturn(user);

		// Perform the request
		ResultActions resultActions =mockMvc.perform(post(BASE_URL+"/create",user).contentType(
				MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user)));
				
		System.out.println(user);
		// Print the result for debugging
		resultActions.andDo(print());

		// Verify the response status
        resultActions.andExpect(status().isCreated())
                     .andExpect(jsonPath("$.firstName", is("Mayur")))  // Check for firstName
                     .andExpect(jsonPath("$.lastName", is("Bhosale"))); // Optionally, check other fields

	}
}
