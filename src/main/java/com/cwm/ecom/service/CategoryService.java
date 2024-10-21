package com.cwm.ecom.service;

import java.util.List;

import com.cwm.ecom.model.Category;

public interface CategoryService {

	Category addCategory(Category category);
	
	List<Category> allCategories();
	
	Category findById(Long id);
	
}
