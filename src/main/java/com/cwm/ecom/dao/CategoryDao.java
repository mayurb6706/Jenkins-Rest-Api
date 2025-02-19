package com.cwm.ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwm.ecom.model.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long>{

}
