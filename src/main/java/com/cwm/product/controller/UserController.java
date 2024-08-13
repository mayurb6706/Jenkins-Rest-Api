package com.cwm.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.product.model.User;
import com.cwm.product.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
public class UserController {
@Autowired
private UserServiceImpl userService;

//save user
@PostMapping
@ResponseStatus(value = HttpStatus.CREATED)
public User saveUser(@RequestBody User user) {
	return userService.saveUser(user);
}

@GetMapping("/{id}")
@ResponseStatus(value = HttpStatus.OK)
public User getSingleUser(@PathVariable("id") Long userId) {
	return userService.getSingleUser(userId);
}

@GetMapping
@ResponseStatus(value = HttpStatus.OK)
public List<User> getAllUsers() {
	return userService.findAllUsers();
}
}
