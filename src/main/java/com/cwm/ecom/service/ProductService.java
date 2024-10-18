package com.cwm.ecom.service;

import java.util.List;

import com.cwm.ecom.entity.ProductRequest;
import com.cwm.ecom.entity.ProductResponse;

public interface ProductService {

	 ProductResponse addProduct(ProductRequest product);
	 List<ProductResponse> getAllProduct();
	 List<ProductResponse> searchProductsByName(String name);
	 ProductResponse getProductById(Long id);
	 
	 ProductResponse updateProduct(Long productId,ProductRequest  productRequest);
	 
	 void deleteProduct(Long productId);

	
}
