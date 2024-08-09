package com.cwm.product.service;

import java.util.List;

import com.cwm.product.entity.ProductRequest;
import com.cwm.product.entity.ProductResponse;

public interface ProductService {

	 ProductResponse addProduct(ProductRequest product);
	 List<ProductResponse> getAllProduct();
	 ProductResponse getProductById(Long id);
	 
	 ProductResponse updateProduct(Long productId,ProductRequest  productRequest);

	
}
