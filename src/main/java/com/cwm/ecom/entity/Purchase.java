package com.cwm.ecom.entity;
import java.util.Set;

import com.cwm.ecom.model.Address;
import com.cwm.ecom.model.Customer;
import com.cwm.ecom.model.Order;
import com.cwm.ecom.model.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Purchase {

	private Customer customer;
	private Order order;
	private Address shippingAddress;
	private Address billingAddress;
	private Set<OrderItem> orderItems;
}
