package com.cwm.ecom.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cwm.ecom.dao.RoleDao;
import com.cwm.ecom.dao.UserDao;
import com.cwm.ecom.model.User;
import com.cwm.ecom.model.UserRole;
import com.cwm.ecom.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserDao userDao;

	private final RoleDao roleDao;

	public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
		this.userDao = userDao;
		this.roleDao = roleDao;
	}

	@Override
	public User saveUser(User user, Set<UserRole> userRole) throws Exception {
		Optional<User> existingUser = this.findByUsername(user.getUsername());
		if (existingUser == null && existingUser.isPresent()) {
			System.out.println(existingUser);
			throw new Exception("User already Exist!");
		} else {
			for (UserRole role : userRole) {
				System.out.println("User roles" + role.toString());
				roleDao.save(role.getRole());
				
			}
			if (user.getUserRoles() == null) {
		        user.setUserRoles(new HashSet<>());
		    }
		    user.getUserRoles().addAll(userRole);

		}
		userDao.save(user);
		return user;

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

		Optional<User> optionalUser = this.userDao.findByUsername(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

			Set<GrantedAuthority> authorities = user.getUserRoles().stream()
					.map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName()))
					.collect(Collectors.toSet());

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					authorities);
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
}