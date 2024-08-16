package com.cwm.ecom.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cwm.ecom.dao.AddressDao;
import com.cwm.ecom.dao.UserDao;
import com.cwm.ecom.model.Address;
import com.cwm.ecom.model.User;
import com.cwm.ecom.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (user.getAddress() != null) {
			Address address = addressDao.save(user.getAddress());
			user.setAddress(address);
		}
		User savedUser=userDao.save(user);
		return savedUser;
	}

	@Override
	public List<User> findAllUsers() {

		return userDao.findAll();
	}

	@Override
	public User getSingleUser(Long userId) {
		User user = userDao.findById(userId).orElseThrow();
		return user;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		Optional<User> user = this.userDao.findByUsername(username);
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		Optional<User> optionalUser = this.userDao.findByUsername(username);
		if (!optionalUser.isEmpty()) {
			user = optionalUser.get();
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					user.getRole().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
		} else
			throw new UsernameNotFoundException("User not found.");

	}
}
