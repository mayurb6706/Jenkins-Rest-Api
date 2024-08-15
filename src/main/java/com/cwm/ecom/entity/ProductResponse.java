package com.cwm.ecom.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductResponse {
	private Long id;
	private String sku;
	private String name;
	private Double unitPrice;
	private int unitsInStock;
	private String image;
	private Date dateCreadted;
	private Date lastUpdated;
	private String description;
//	TODO: Create a category
	private Long categeoryId;
}
