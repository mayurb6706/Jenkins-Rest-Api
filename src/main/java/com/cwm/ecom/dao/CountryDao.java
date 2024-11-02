package com.cwm.ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwm.ecom.model.Country;

@Repository
public interface CountryDao extends JpaRepository<Country, Integer> {

}
