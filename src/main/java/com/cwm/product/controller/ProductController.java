package com.cwm.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.product.entity.ProductRequest;
import com.cwm.product.entity.ProductResponse;
import com.cwm.product.service.impl.ProductServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/product")
@Tag(name = "Product Rest Api", description = "Product management APIs")
public class ProductController {

	@Autowired
	private ProductServiceImpl productService;

	// add new product
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProductResponse addProduct(@RequestBody ProductRequest product) {

		ProductResponse response = this.productService.addProduct(product);
		System.out.println(response);
		return response;
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

	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ProductResponse updateProduct(@PathVariable(value = "id") Long productId,
			@RequestBody ProductRequest productRequest) {

		ProductResponse productResponse = this.productService.updateProduct(productId, productRequest);

		return productResponse;
	}

}
