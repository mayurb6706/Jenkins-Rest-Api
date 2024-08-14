package com.cwm.ecom.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwm.ecom.model.User;

@Repository
public interface UserDao  extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}
