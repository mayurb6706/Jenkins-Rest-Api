package com.cwm.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.ecom.model.User;
import com.cwm.ecom.service.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Api")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;

	@PostMapping("/create")
	@ResponseStatus(value = HttpStatus.CREATED)
	public User saveUser(@RequestBody User user) {
		return userService.saveUser(user);
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
