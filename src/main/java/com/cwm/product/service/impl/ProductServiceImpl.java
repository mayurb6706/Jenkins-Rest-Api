package com.cwm.product.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cwm.product.dao.ProductDao;
import com.cwm.product.entity.ProductRequest;
import com.cwm.product.entity.ProductResponse;
import com.cwm.product.exception.ProductNotFoundException;
import com.cwm.product.model.Product;
import com.cwm.product.service.ProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	private ProductDao prodDao;
	
	@Override
	public ProductResponse addProduct(ProductRequest product) {
		Product prod= new Product();
		BeanUtils.copyProperties(product, prod);
		prodDao.save(prod);
		ProductResponse response= ProductResponse.builder()
				.name(prod.getName())
				.price(prod.getPrice())
				.quantity(prod.getQuantity())
				.id(prod.getId())
				.image(prod.getImage())
				.build();
		return response;
	}

	@Override
	public List<ProductResponse> getAllProduct() {
		List<Product> productList=this.prodDao.findAll();
		List<ProductResponse> responseList=productList.stream().map(prod->{
			ProductResponse response= ProductResponse.builder()
					.name(prod.getName())
					.price(prod.getPrice())
					.quantity(prod.getQuantity())
					.id(prod.getId())
					.image(prod.getImage())
					.build();
			return response;
		}).collect(Collectors.toList());
		return responseList;
	}

	@Override
	public ProductResponse getProductById(Long id) {
	Product prod= this.prodDao.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not exit with id "+id));
	ProductResponse response=ProductResponse.builder()
			.name(prod.getName())
			.price(prod.getPrice())
			.quantity(prod.getQuantity())
			.id(prod.getId())
			.image(prod.getImage())
			.build();
	return response;
	}
	
	

}
