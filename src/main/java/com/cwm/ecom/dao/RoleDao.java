package com.cwm.ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwm.ecom.model.Role;

@Repository
public interface RoleDao  extends JpaRepository<Role, Long>{

	Role findByName(String roleName);
}
