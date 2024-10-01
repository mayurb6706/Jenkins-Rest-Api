package com.cwm.ecom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwm.ecom.dao.CategoryDao;
import com.cwm.ecom.model.Category;
import com.cwm.ecom.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	
	@Override
	public Category addCategory(Category category) {
		return this.categoryDao.save(category);
	}

	@Override
	public List<Category> allCategories() {
		return this.categoryDao.findAll();
	}

}
