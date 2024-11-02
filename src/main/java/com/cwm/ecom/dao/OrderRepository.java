package com.cwm.ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cwm.ecom.model.Order;

public interface OrderRepository  extends JpaRepository<Order, Long>{

	Order findByCustomerEmail(@Param("email") String email);
	
	Order findByOrderTrackingId(String trackingId);
}
