package com.cwm.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.ecom.entity.ProductResponse;
import com.cwm.ecom.service.impl.ProductServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/product")
@Tag(name = "Product Api", description = "Product  APIs")

@CrossOrigin("http://localhost:4200")

public class ProductController {

	
	private final ProductServiceImpl productService;

	@Autowired
	public ProductController(ProductServiceImpl impl) {
		this.productService=impl;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		ProductResponse response = this.productService.getProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProduct() {
		List<ProductResponse> list = this.productService.getAllProduct();
		return ResponseEntity.status(HttpStatus.OK).body(list);

	}


	@GetMapping("/name")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<ProductResponse>> getProductByName(@RequestParam String name){
		List<ProductResponse> productResponses= this.productService.searchProductsByName(name);
		return ResponseEntity.ok(productResponses);
	}
}
