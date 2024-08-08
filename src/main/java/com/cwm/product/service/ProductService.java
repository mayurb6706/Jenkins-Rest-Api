package com.cwm.product.service;

import java.util.List;

import com.cwm.product.entity.ProductRequest;
import com.cwm.product.entity.ProductResponse;

public interface ProductService {

	public ProductResponse addProduct(ProductRequest product);
	public List<ProductResponse> getAllProduct();
	public ProductResponse getProductById(Long id);
	
}
