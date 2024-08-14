package com.cwm.ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwm.ecom.model.Address;

@Repository
public interface AddressDao extends JpaRepository<Address, Long> {

}
