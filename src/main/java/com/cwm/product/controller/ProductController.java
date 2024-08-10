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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	@Operation(summary = "Add a new product", description = "Adds a new product to the system")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Product created"),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	public ProductResponse addProduct(@RequestBody ProductRequest product) {

		ProductResponse response = this.productService.addProduct(product);
		System.out.println(response);
		return response;
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a product by ID", description = "Retrieve a product by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product found"),
			@ApiResponse(responseCode = "404", description = "Product not found") })
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		ProductResponse response = this.productService.getProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	@Operation(summary = "Get all products", description = "Retrieve a list of all products")
	public ResponseEntity<List<ProductResponse>> getAllProduct() {
		List<ProductResponse> list = this.productService.getAllProduct();
		return ResponseEntity.status(HttpStatus.OK).body(list);

	}

	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	@Operation(summary = "Update a product", description = "Update the details of an existing product")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product updated"),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "404", description = "Product not found") })
	public ProductResponse updateProduct(@PathVariable(value = "id") Long productId,
			@RequestBody ProductRequest productRequest) {

		ProductResponse productResponse = this.productService.updateProduct(productId, productRequest);

		return productResponse;
	}

}
