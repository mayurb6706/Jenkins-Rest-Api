package com.cwm.ecom.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private UserController userController;


	@Autowired
	private ObjectMapper mapper=new ObjectMapper();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		
	}

	@Test
	public void testSaveUser() throws Exception {
		Set<String> roles = new HashSet<>();
		roles.add("USER");

		Address address = Address.builder().state("Maharashtra").street("Karvenager").city("Pune").country("INDIA").pin("11111")
				.build();

		User user = User.builder().firstName("Mayur").lastName("Bhosale").email("mayur@test.com").contact("1234567890")
				.password("password").username("username").address(address).role(roles).build();
		// Mock the behavior of userService
		BDDMockito.given(userService.saveUser(any(User.class))).willReturn(user);

		// Perform the request
		ResultActions resultActions = mockMvc.perform(post(BASE_URL + "/create")
				.contentType("application/json").content("\"id\": 1,\r\n"
						+ "  \"firstName\": \"string\",\r\n"
						+ "  \"lastName\": \"string\",\r\n"
						+ "  \"email\": \"string\",\r\n"
						+ "  \"contact\": \"string\",\r\n"
						+ "  \"username\": \"string\",\r\n"
						+ "  \"password\": \"$2a$10$9wMZnVz1PzXZ86U4o19yP.OWOWHnOYfOAd7LEk9lmEG8ks.biu5xa\",\r\n"
						+ "  \"address\": {\r\n"
						+ "    \"id\": 1,\r\n"
						+ "    \"street\": \"string\",\r\n"
						+ "    \"state\": \"string\",\r\n"
						+ "    \"city\": \"string\",\r\n"
						+ "    \"country\": \"string\",\r\n"
						+ "    \"pin\": \"string\"\r\n"
						+ "  },\r\n"
						+ "  \"role\": [\r\n"
						+ "    \"string\"\r\n"
						+ "  ]"));
		System.out.println(user);
		// Print the result for debugging
		resultActions.andDo(print());

		// Verify the response status
//        resultActions.andExpect(status().isCreated())
//                     .andExpect(jsonPath("$.firstName", is("Mayur")))  // Check for firstName
//                     .andExpect(jsonPath("$.lastName", is("Bhosale"))); // Optionally, check other fields

	}
}
