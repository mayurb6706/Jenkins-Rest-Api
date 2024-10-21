package com.cwm.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.ecom.model.Category;
import com.cwm.ecom.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("http://localhost:4200")
public class CategoryController {

	private final CategoryServiceImpl serviceImpl;
	
	@Autowired
	public CategoryController(CategoryServiceImpl categoryServiceImpl) {
		this.serviceImpl=categoryServiceImpl;
	}	
	@GetMapping("/all")
	public List<Category> getAllCategories(){
		List<Category> categories= this.serviceImpl.allCategories();
		return categories;
	}
	
	@GetMapping("/id")
	public ResponseEntity<Category> findById(@RequestParam Long id){
		Category category=this.serviceImpl.findById(id);
		return ResponseEntity.ok(category);
	}
}
