package com.cwm.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.ecom.model.Category;
import com.cwm.ecom.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl serviceImpl;
	
	@PostMapping("/add")
	public Category addCategory(@RequestBody Category category) {
		Category cat= this.serviceImpl.addCategory(category);
		return cat;
	}
	
	@GetMapping("/all")
	public List<Category> getAllCategories(){
		List<Category> categories= this.serviceImpl.allCategories();
		return categories;
	}
}
