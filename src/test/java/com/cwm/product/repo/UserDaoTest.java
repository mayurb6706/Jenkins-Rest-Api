package com.cwm.product.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cwm.product.dao.UserDao;
import com.cwm.product.model.Address;
import com.cwm.product.model.User;

@DataJpaTest
public class UserDaoTest {

	@Autowired
	private UserDao userDao;

	private User user1, user2;
	
	private Address address;
	
	Set<String> roles= new HashSet<>();

	@BeforeEach
	public void init() {
		
		roles.add("Admin");
		address=Address.builder().street("karvenager").city("Pune").state("Maharashtra").country("INDIA").pin("411052").build();
		user1 = User.builder().firstName("Mayur").lastName("Bhosale").email("mayur@test.com")
				.contact("1234567890").username("username").password("password").address(address).build();
		user2 = User.builder().firstName("Shyam").lastName("Kadam").email("shyam@test.com").contact("1234567891")
				.username("username1").password("password1").address(address).role(roles).build();
	}

	@Test
	public void saveUserEntity() {
		User u1 = this.userDao.save(user1);

		assertThat(u1).isNotNull();
		assertThat(u1.getEmail()).isEqualTo(u1.getEmail());
		assertThat(u1.getUsername()).isEqualTo(u1.getUsername());
		assertThat(u1.getContact()).isEqualTo(u1.getContact());
		
		//Check Address Details
		assertThat(u1.getAddress().getCity()).isEqualTo("Pune");
		assertThat(u1.getAddress().getState()).isEqualTo("Maharashtra");
		assertThat(u1.getAddress().getStreet()).isEqualTo("karvenager");
		assertThat(u1.getAddress().getCountry()).isEqualTo("INDIA");
	}

	@Test
	public void getUserById() {
		this.userDao.save(user1);
		User u1 = this.userDao.findById(user1.getId()).orElseThrow();
		assertThat(u1).isNotNull();
		assertThat(u1.getEmail()).isEqualTo(u1.getEmail());
		assertThat(u1.getUsername()).isEqualTo(u1.getUsername());
		assertThat(u1.getContact()).isEqualTo(u1.getContact());
	}

	@Test
	public void getAllUsers() {

		userDao.save(user1);
		userDao.save(user2);

		List<User> list = this.userDao.findAll();

		assertThat(list).isNotEmpty();

		assertThat(list.get(0).getEmail()).isEqualTo("mayur@test.com");
		assertThat(list.get(1).getEmail()).isEqualTo("shyam@test.com");

	}

	
	
}
