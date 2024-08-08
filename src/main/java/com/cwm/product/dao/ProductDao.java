package com.cwm.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwm.product.model.Product;

@Repository
public interface ProductDao  extends JpaRepository<Product, Long>{

}
