package com.cwm.ecom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.cwm.ecom.model.Product;

@Repository
public interface ProductDao  extends JpaRepository<Product, Long>{
	List<Product> findByNameContainingIgnoreCase(@RequestParam String name);
}
