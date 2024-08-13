package com.cwm.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwm.product.model.User;

@Repository
public interface UserDao  extends JpaRepository<User, Long>{

}
