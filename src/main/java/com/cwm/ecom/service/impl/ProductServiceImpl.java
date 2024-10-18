package com.cwm.ecom.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwm.ecom.dao.ProductDao;
import com.cwm.ecom.entity.ProductRequest;
import com.cwm.ecom.entity.ProductResponse;
import com.cwm.ecom.exception.ProductNotFoundException;
import com.cwm.ecom.model.Product;
import com.cwm.ecom.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductDao prodDao;
	
	@Autowired
	public ProductServiceImpl(ProductDao dao) {
		this.prodDao=dao;
	}
	@Override
	public ProductResponse addProduct(ProductRequest product) {
		
		Product prod= new Product();
		BeanUtils.copyProperties(product, prod);
		prodDao.save(prod);
		ProductResponse response= ProductResponse.builder()
				.name(prod.getName())
				.sku(prod.getSku())
				.unitPrice(prod.getUnitPrice())
				.unitsInStock(prod.getUnitsInStock())
				.description(prod.getDescription())
				.imageUrl(prod.getImageUrl())
				.dateCreadted(prod.getDateCreated())
				.category(prod.getCategory())
				.build();
		return response;
	}

	@Override
	public List<ProductResponse> getAllProduct() {
		List<Product> productList=this.prodDao.findAll();
		List<ProductResponse> responseList=productList.stream().map(prod->{
			ProductResponse response= ProductResponse.builder()
					.id(prod.getId())
					.name(prod.getName())
					.sku(prod.getSku())
					.unitPrice(prod.getUnitPrice())
					.unitsInStock(prod.getUnitsInStock())
					.description(prod.getDescription())
					.imageUrl(prod.getImageUrl())
					.dateCreadted(prod.getDateCreated())
					.category(prod.getCategory())
					.build();
			return response;
		}).collect(Collectors.toList());
		return responseList;
	}

	@Override
	public ProductResponse getProductById(Long id) {
	Product prod= this.prodDao.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not exit with id "+id));
	ProductResponse response=ProductResponse.builder()
			.id(id)
			.name(prod.getName())
			.sku(prod.getSku())
			.unitPrice(prod.getUnitPrice())
			.unitsInStock(prod.getUnitsInStock())
			.description(prod.getDescription())
			.imageUrl(prod.getImageUrl())
			.dateCreadted(prod.getDateCreated())
			.category(prod.getCategory())
			.build();
	return response;
	}

	@Override
	public ProductResponse updateProduct(Long productId,ProductRequest productRequest) {
		Product product= this.prodDao.findById(productId).get();
		if(product!=null) {
		BeanUtils.copyProperties(productRequest, product);
		prodDao.save(product);
		}
		
		return ProductResponse.builder()
				.name(product.getName())
				.sku(product.getSku())
				.unitPrice(product.getUnitPrice())
				.unitsInStock(product.getUnitsInStock())
				.description(product.getDescription())
				.imageUrl(product.getImageUrl())
				.dateCreadted(product.getDateCreated())
				.category(product.getCategory())
				.build();
	}

	@Override
	public void deleteProduct(Long productId) {
	
		Product productFromDB= this.prodDao.findById(productId).orElseThrow(()->new ProductNotFoundException("Product not exit with id "+productId));	
		prodDao.delete(productFromDB);
	}
	@Override
	public List<ProductResponse> searchProductsByName(String name) {
		List<Product> products= this.prodDao.findByNameContainingIgnoreCase(name);
		List<ProductResponse> responseList=products.stream().map(prod->{
			ProductResponse response= ProductResponse.builder()
					.id(prod.getId())
					.name(prod.getName())
					.sku(prod.getSku())
					.unitPrice(prod.getUnitPrice())
					.unitsInStock(prod.getUnitsInStock())
					.description(prod.getDescription())
					.imageUrl(prod.getImageUrl())
					.dateCreadted(prod.getDateCreated())
					.category(prod.getCategory())
					.build();
			return response;
		}).collect(Collectors.toList());
		return responseList;
	}


}
