package com.cwm.product.exception;


public class ProductNotFoundException  extends RuntimeException{

	public ProductNotFoundException() {
		super("Product does not exit!");
	}
	
	public ProductNotFoundException(String message) {
		super(message);
	}
}
