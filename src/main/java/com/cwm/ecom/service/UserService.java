package com.cwm.ecom.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.cwm.ecom.model.User;
import com.cwm.ecom.model.UserRole;

public interface UserService {

	public User saveUser(User user,Set<UserRole> userRole) throws Exception;
	public List<User> findAllUsers();
	public User getSingleUser(Long userId);
	Optional<User> findByUsername(String username);
}
