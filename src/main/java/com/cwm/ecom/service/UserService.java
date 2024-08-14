package com.cwm.ecom.service;

import java.util.List;
import java.util.Optional;

import com.cwm.ecom.model.User;

public interface UserService {

	public User saveUser(User user);
	public List<User> findAllUsers();
	public User getSingleUser(Long userId);
	Optional<User> findByUsername(String username);
}
