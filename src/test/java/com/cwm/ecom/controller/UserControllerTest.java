package com.cwm.ecom.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		Set<String> roles = new HashSet<>();
		roles.add("USER");


		user = User.builder().id(1L).firstName("Mayur").lastName("Bhosale").email("mayur@test.com")
				.contact("1234567890").password("password").username("username").role(roles).build();
	}

	@Test
	public void testSaveUser() throws Exception {
		given(userService.saveUser(any(User.class))).willReturn(user);
		ResultActions resultActions = mockMvc.perform(post(BASE_URL + "/create", user)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user)));

		System.out.println(user);
		resultActions.andDo(print());

		resultActions.andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is("Mayur"))) // Check for
																										// firstName
				.andExpect(jsonPath("$.lastName", is("Bhosale"))); // Optionally, check other fields

	}

	@Test
	public void testGetSingleUser() throws Exception {
		given(userService.getSingleUser(anyLong())).willReturn(user);

		ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/{id}", 1L));

		resultActions.andDo(print());

	}

	@Test
	public void testGetAllUsers() throws Exception {
		List<User> users = new ArrayList<>();
		users.add(user);
		given(userService.findAllUsers()).willReturn(users);

		ResultActions resultActions = mockMvc.perform(get(BASE_URL+"/all-users"));

		resultActions.andDo(print());
		
	}
	
	@Test
	public void testFindByUsername()  throws Exception{
		String username="username";
		
		given(userService.findByUsername(username)).willReturn(Optional.of(user));
		
		ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/username?username=username", username));
		
		resultActions.andDo(print());

		
	}
}
