package com.cwm.ecom.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.ecom.dao.RoleDao;
import com.cwm.ecom.model.Role;
import com.cwm.ecom.model.User;
import com.cwm.ecom.model.UserRole;
import com.cwm.ecom.service.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Api")
@CrossOrigin("http://localhost:4200")
public class UserController {
	
	
	private final UserServiceImpl userService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final RoleDao roleDao;

	@Autowired
	public UserController(UserServiceImpl userImpl,BCryptPasswordEncoder passwordEncoder,RoleDao roleDao) {
		this.userService=userImpl;
		this.passwordEncoder=passwordEncoder;
		this.roleDao=roleDao;
	}
	@PostMapping("/create")
	@ResponseStatus(value = HttpStatus.CREATED)
	public User saveUser(@RequestBody User user) throws Exception {
	
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<UserRole> roles = new HashSet<>();
		 Role role = roleDao.findByName("USER");
	        UserRole userRole = new UserRole();
	        userRole.setUser(user);
	        userRole.setRole(role);

	        roles.add(userRole);

		User savedUser=this.userService.saveUser(user, roles);
		return savedUser;
	}

	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public User getSingleUser(@PathVariable("id") Long userId) {
		return userService.getSingleUser(userId);
	}

	@GetMapping("/all-users")
	@ResponseStatus(value = HttpStatus.OK)
	public List<User> getAllUsers() {
		return userService.findAllUsers();
	}

	@GetMapping("/username")
	@ResponseStatus(value = HttpStatus.OK)
	public User getByUsername(@RequestParam String username) {
		return userService.findByUsername(username).get();
	}
}
