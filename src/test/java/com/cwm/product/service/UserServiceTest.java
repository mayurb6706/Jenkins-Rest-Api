package com.cwm.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cwm.product.dao.UserDao;
import com.cwm.product.model.User;
import com.cwm.product.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;

	private User user1, user2;
	private List<User> usersList = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		user1 = User.builder().firstName("Mayur").lastName("Bhosale").email("mayur@test.com").contact("1234567890")
				.username("username").password("password").build();
		user2 = User.builder().firstName("Shyam").lastName("Kadam").email("shyam@test.com").contact("1234567891")
				.username("username1").password("password1").build();

		usersList.add(user1);
		usersList.add(user2);
	}

	@Test
	void testSaveUser() {
		  when(this.userDao.save(user1)).thenReturn(user1);
		  
		  User user= this.userService.saveUser(user1);
		  
		  assertThat(user).isNotNull();
		  assertThat(user.getUsername()).isEqualTo("username");
		  assertThat(user.getFirstName()).isEqualTo("Mayur");
		  assertThat(user.getLastName()).isEqualTo("Bhosale");
		  assertThat(user.getPassword()).isEqualTo("password");
		  assertThat(user.getEmail()).isEqualTo("mayur@test.com");
		  assertThat(user.getContact()).isEqualTo("1234567890");
	}

	@Test
	void testFindAllUsers() {
		
		when(userDao.findAll()).thenReturn(usersList);
		
		List<User> users= userService.findAllUsers();
		
		assertThat(users.size()).isEqualTo(2);
		assertThat(users.get(0).getFirstName()).isEqualTo("Mayur");
		assertThat(users.get(1).getLastName()).isEqualTo("Kadam");
		assertThat(users.get(0).getEmail()).isEqualTo("mayur@test.com");
		assertThat(users.get(1).getUsername()).isEqualTo("username1");
		assertThat(users.get(0).getPassword()).isEqualTo("password");
		
	}

	@Test
	void testGetSingleUser() {

		when(userDao.findById(anyLong())).thenReturn(Optional.of(user2));
		
		User user= userService.getSingleUser(1L);
		 assertThat(user).isNotNull();
		  assertThat(user.getUsername()).isEqualTo("username1");
		  assertThat(user.getFirstName()).isEqualTo("Shyam");
		  assertThat(user.getLastName()).isEqualTo("Kadam");
		  assertThat(user.getPassword()).isEqualTo("password1");
		  assertThat(user.getEmail()).isEqualTo("shyam@test.com");
		  assertThat(user.getContact()).isEqualTo("1234567891");
		
	}

}
