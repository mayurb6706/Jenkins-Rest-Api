package com.cwm.ecom.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String orderTrackingId;
	
	private int totalQuantity;
	
	private Double totalPrice;
	
	private String status;
	
	@CreationTimestamp
	private Date dateCreated;
	
	@UpdateTimestamp
	private Date lastUpdated;
	
	//One order has many order items
	//Create a set of order item
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private Set<OrderItem> orderItems=new HashSet<>();
	
	//Many Customer has one order
	//Same product order by different cutomer
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	//Address mapping is one to one
	//As every single customer has a one address for shipping and billing
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private Address shippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    private Address billingAddress;
    
    public void addItem(OrderItem order) {
    	if(order!=null) {
    		if(orderItems==null) {
    			  orderItems = new HashSet<>();
            }
            orderItems.add(order);
            order.setOrder(this);
    	}
    }

	

}
