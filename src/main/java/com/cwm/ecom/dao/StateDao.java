package com.cwm.ecom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwm.ecom.model.State;

@Repository
public interface StateDao extends JpaRepository<State, Integer> {

	List<State> findByCountryId(int countryId);
}
