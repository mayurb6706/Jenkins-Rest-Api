package com.cwm.ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cwm.ecom.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Customer findByEmail(String email);

}
