//package com.cwm.ecom.repo;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import com.cwm.ecom.dao.UserDao;
//import com.cwm.ecom.model.User;
//
//@DataJpaTest
//public class UserDaoTest {
//
//	@Autowired
//	private UserDao userDao;
//	
//
//	private User user1;
//
//	private Set<String> roles = new HashSet<>();
//
//	@BeforeEach
//	public void init() {
//
//		roles.add("Admin");
//		
//		user1 = User.builder().firstName("Mayur").lastName("Bhosale").email("mayur@test.com").contact("1234567890")
//				.username("username").password("password").build();
//
//	}
//
//	@Test
//	public void saveUserEntity() {
//
//		User u1 = this.userDao.save(user1);
//
//		assertThat(u1).isNotNull();
//		assertThat(u1.getEmail()).isEqualTo(u1.getEmail());
//		assertThat(u1.getUsername()).isEqualTo(u1.getUsername());
//		assertThat(u1.getContact()).isEqualTo(u1.getContact());
//
//	}
//
//	@Test
//	public void getUserById() {
//		this.userDao.save(user1);
//		User u1 = this.userDao.findById(user1.getId()).orElseThrow();
//		assertThat(u1).isNotNull();
//		assertThat(u1.getEmail()).isEqualTo(u1.getEmail());
//		assertThat(u1.getUsername()).isEqualTo(u1.getUsername());
//		assertThat(u1.getContact()).isEqualTo(u1.getContact());
//
//	}
//
//	@Test
//	public void getAllUsers() {
//
//		userDao.save(user1);
//
//		List<User> list = this.userDao.findAll();
//		System.out.println(list);
//
//		assertThat(list).isNotEmpty();
//
//		assertThat(list.get(0).getEmail()).isEqualTo("mayur@test.com");
//	
//
//	}
//
//	@Test
//	public void testFindByUsername() {
//
//		userDao.save(user1);
//		User u1 = userDao.findByUsername(user1.getUsername()).get();
//		assertThat(u1).isNotNull();
//		assertThat(u1.getEmail()).isEqualTo(u1.getEmail());
//		assertThat(u1.getUsername()).isEqualTo(u1.getUsername());
//		assertThat(u1.getContact()).isEqualTo(u1.getContact());
//
//	
//	}
//
//}
