package com.cwm.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwm.ecom.entity.Purchase;
import com.cwm.ecom.entity.PurchaseResponce;
import com.cwm.ecom.service.CheckoutService;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin("http://localhost:4200")
public class CheckoutController {

	
	@Autowired
	private CheckoutService service;
	
	//place the order 
   @PostMapping("/purchase")
   public PurchaseResponce placeOrder(@RequestBody Purchase purchase) {
	   
	   return this.service.placeOrder(purchase);
   }
}