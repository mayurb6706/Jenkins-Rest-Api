package com.cwm.product.controller;

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
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cwm.product.model.User;
import com.cwm.product.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

	private static final String BASE_URL="/api/user";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserServiceImpl userService;
	
	@Autowired
	private ObjectMapper mapper;
	private User user1, user2;
	
	private List<User> userList= new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		
		user1 = User.builder().firstName("Mayur").lastName("Bhosale").email("mayur@test.com").contact("1234567890")
				.username("username").password("password").build();
		user2 = User.builder().firstName("Shyam").lastName("Kadam").email("shyam@test.com").contact("1234567891")
				.username("username1").password("password1").build();

		userList.add(user1);
		userList.add(user2);
	}

	@Test
	void testSaveUser() throws Exception {
		
		given(userService.saveUser(any(User.class))).willReturn(user1);
		
		ResultActions resultActions= mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(BASE_URL)
				.content(mapper.writeValueAsString(user1)));
		
		resultActions.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.firstName",is("Mayur")))
		.andExpect(jsonPath("$.lastName", is("Bhosale")))
		.andExpect(jsonPath("$.email", is("mayur@test.com")))
		.andExpect(jsonPath("$.username", is("username")))
		.andExpect(jsonPath("$.password", is("password")))
		.andExpect(jsonPath("$.contact", is("1234567890")));
			
	}

	@Test
	void testGetSingleUser() throws Exception {
	
		given(userService.getSingleUser(anyLong())).willReturn(user1);
		ResultActions resultActions= mockMvc.perform(get(BASE_URL+"/{id}", 1L));
		
		resultActions.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName",is("Mayur")))
		.andExpect(jsonPath("$.lastName", is("Bhosale")))
		.andExpect(jsonPath("$.email", is("mayur@test.com")))
		.andExpect(jsonPath("$.username", is("username")))
		.andExpect(jsonPath("$.password", is("password")))
		.andExpect(jsonPath("$.contact", is("1234567890")));
	}

	@Test
	void testGetAllUsers()  throws Exception{
		given(userService.findAllUsers()).willReturn(userList);
		ResultActions resultActions= mockMvc.perform(get(BASE_URL));
		
		resultActions.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.size()",is(2)))
		.andExpect(jsonPath("$.[0].firstName", is("Mayur")))
		.andExpect(jsonPath("$.[1].lastName", is("Kadam")))
		.andExpect(jsonPath("$.[0].email", is("mayur@test.com")))
		.andExpect(jsonPath("$.[1].contact", is("1234567891")));
	}

}
