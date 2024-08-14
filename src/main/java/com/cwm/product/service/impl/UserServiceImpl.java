package com.cwm.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwm.product.dao.AddressDao;
import com.cwm.product.dao.UserDao;
import com.cwm.product.model.Address;
import com.cwm.product.model.User;
import com.cwm.product.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AddressDao addressDao;
	
	

	@Override
	public User saveUser(User user) {
		// TODO: encrypt password
		System.out.println(user.getAddress());
		if (user.getAddress() != null) {	
			Address address= addressDao.save(user.getAddress());
			user.setAddress(address);
		}
		return userDao.save(user);
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

}
