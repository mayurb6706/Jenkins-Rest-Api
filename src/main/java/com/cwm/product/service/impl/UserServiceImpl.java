package com.cwm.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwm.product.dao.UserDao;
import com.cwm.product.model.User;
import com.cwm.product.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User saveUser(User user) {
		//TODO: encrypt password
		return userDao.save(user);
	}

	@Override
	public List<User> findAllUsers() {
		
		return userDao.findAll();
	}

	@Override
	public User getSingleUser(Long userId) {
		User user= userDao.findById(userId).orElseThrow() ;
		return user;
	}

}
