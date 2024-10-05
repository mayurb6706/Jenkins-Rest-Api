package com.cwm.ecom.entity;

import java.util.Date;

import com.cwm.ecom.model.Category;

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
public class ProductRequest {
	private Long id;
	private String sku;
	private String name;
	private Double unitPrice;
	private int unitsInStock;
	private String imageUrl;
	private Date dateCreadted;
	private Boolean active;
	private String description;

	private Category category;

}
