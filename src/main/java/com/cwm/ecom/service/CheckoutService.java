package com.cwm.ecom.service;


import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cwm.ecom.dao.CustomerRepository;
import com.cwm.ecom.entity.Purchase;
import com.cwm.ecom.entity.PurchaseResponce;
import com.cwm.ecom.model.Customer;
import com.cwm.ecom.model.Order;
import com.cwm.ecom.model.OrderItem;

import jakarta.transaction.Transactional;

@Service
public class CheckoutService {

	private CustomerRepository repo;

	public CheckoutService(CustomerRepository repo) {
		this.repo = repo;
	}

	@Transactional
	public PurchaseResponce placeOrder(Purchase purchase) {

		// get the order
		Order order = purchase.getOrder();

		// generating the order tracking id and set to the order
		String orderTrackingId = generatingOrderTrackingId();
		order.setOrderTrackingId(orderTrackingId);

		// set a single order items to the order
		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item -> order.addItem(item));

		// populating the billing and shipping address
		order.setShippingAddress(purchase.getShippingAddress());
		order.setBillingAddress(purchase.getBillingAddress());

		// get a customer details
		Customer customer = purchase.getCustomer();
		
		//Check the Customer with same email exist or not
		
		String theEmail=customer.getEmail();
		
		Customer cutomerFromDB=this.repo.findByEmail(theEmail);
		
		//If customer is already exist the do not create a new Customer
		if(cutomerFromDB!=null) {
			customer=cutomerFromDB;
		}

		// and add order to a customer
		customer.add(order);

		// save the customer
		repo.save(customer);
		// return the purchase response id

		return new PurchaseResponce(orderTrackingId);
	}

	private String generatingOrderTrackingId() {

		return UUID.randomUUID().toString();
	}
}
