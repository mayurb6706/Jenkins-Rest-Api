package com.cwm.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cwm.product.dao.AddressDao;
import com.cwm.product.dao.UserDao;
import com.cwm.product.model.Address;
import com.cwm.product.model.User;
import com.cwm.product.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserDao userDao;

	@Mock
	private AddressDao addressDao;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;


	@InjectMocks
	private UserServiceImpl userDetailsService;

	@InjectMocks
	private UserServiceImpl userService;

	private User user1, user2;
	private Address address;
	private List<User> usersList = new ArrayList<>();
	private Set<String> roles = new HashSet<>();

	@BeforeEach
	void setUp() throws Exception {
		roles.add("Admin");
		address = Address.builder().street("karvenager").city("Pune").state("Maharashtra").country("INDIA")
				.pin("411052").build();
		user1 = User.builder().firstName("Mayur").lastName("Bhosale").email("mayur@test.com").contact("1234567890")
				.username("username").password("password").address(address).role(roles).build();
		user2 = User.builder().firstName("Shyam").lastName("Kadam").email("shyam@test.com").contact("1234567891")
				.username("username1").password("password1").address(address).role(roles).build();

		usersList.add(user1);
		usersList.add(user2);
	}

	@Test
	void testSaveUser() {

		
		
		 when(passwordEncoder.encode(user1.getPassword())).thenReturn("password");
		  when(addressDao.save(address)).thenReturn(address);
		
		  when(this.userDao.save(user1)).thenReturn(user1);
		
		  User user= this.userService.saveUser(user1);
		  System.out.println(user);
		  System.out.println(user);
		  assertThat(user).isNotNull();
		  assertThat(user.getUsername()).isEqualTo("username");
		  assertThat(user.getFirstName()).isEqualTo("Mayur");
		  assertThat(user.getLastName()).isEqualTo("Bhosale");
		  assertThat(user.getPassword()).isEqualTo("password");
		  assertThat(user.getEmail()).isEqualTo("mayur@test.com");
		  assertThat(user.getContact()).isEqualTo("1234567890");
		  
		  //Check Address Details
		  assertThat(user.getAddress().getCountry()).isEqualTo("INDIA");
		  assertThat(user.getAddress().getPin()).isEqualTo("411052");
		  assertThat(user.getAddress().getStreet()).isEqualTo("karvenager");
		  
		 User  user3 = User.builder().firstName("Mayur").lastName("Bhosale").email("mayur@test.com").contact("1234567890")
					.username("username").password("password").address(null).role(roles).build();
		  this.userService.saveUser(user3);
		  
	}

	@Test
	void testFindAllUsers() {
		
		when(userDao.findAll()).thenReturn(usersList);
		
		List<User> users= userService.findAllUsers();
		
		assertThat(users.size()).isEqualTo(2);
		assertThat(users.get(0).getFirstName()).isEqualTo("Mayur");
		assertThat(users.get(0).getAddress().getStreet()).isEqualTo("karvenager");
		assertThat(users.get(1).getLastName()).isEqualTo("Kadam");
		assertThat(users.get(0).getAddress().getCity()).isEqualTo("Pune");
		assertThat(users.get(0).getEmail()).isEqualTo("mayur@test.com");
		assertThat(users.get(0).getAddress().getState()).isEqualTo("Maharashtra");
		assertThat(users.get(1).getUsername()).isEqualTo("username1");
		assertThat(users.get(0).getAddress().getCountry()).isEqualTo("INDIA");
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
		  
		  assertThat(user.getAddress().getCity()).isEqualTo("Pune");
			assertThat(user.getAddress().getPin()).isEqualTo("411052");
		
	}

	@Test 
	void testFindByUsername() {
		when(userDao.findByUsername(anyString())).thenReturn(Optional.of(user2));
		User user= userService.findByUsername("username1").get();
		 assertThat(user).isNotNull();
		  assertThat(user.getUsername()).isEqualTo("username1");
		  assertThat(user.getFirstName()).isEqualTo("Shyam");
		  assertThat(user.getLastName()).isEqualTo("Kadam");
		  assertThat(user.getPassword()).isEqualTo("password1");
		  assertThat(user.getEmail()).isEqualTo("shyam@test.com");
		  assertThat(user.getContact()).isEqualTo("1234567891");
		  
		  assertThat(user.getAddress().getCity()).isEqualTo("Pune");
			assertThat(user.getAddress().getPin()).isEqualTo("411052");
	}

	@Test
	void testLoadByUsername() {
		when(userDao.findByUsername(anyString())).thenReturn(Optional.of(user2));
		User user= userService.findByUsername("username1").get();
		
		  UserDetails userDetails = userService.loadUserByUsername("username1");
		  assertNotNull(userDetails);
	        assertEquals(user.getUsername() ,userDetails.getUsername());
	        assertEquals(user.getPassword(), userDetails.getPassword());
	        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Admin")));
	        verify(userDao,times(2)).findByUsername(anyString());
	        
	        
	        when(userDao.findByUsername(anyString())).thenReturn(Optional.empty());
	        UsernameNotFoundException exception = new UsernameNotFoundException("User not found.");
	        assertThrows(UsernameNotFoundException.class, () -> {
	            userDetailsService.loadUserByUsername("user");
	        });
	        
	        assertEquals("User not found.", exception.getMessage());
	 
	    }

}
