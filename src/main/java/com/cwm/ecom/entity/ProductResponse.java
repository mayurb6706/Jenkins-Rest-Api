package com.cwm.ecom.entity;

import java.util.Date;

import com.cwm.ecom.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private String imageUrl;
	private Date dateCreadted;
	private String description;
	@JsonIgnore
	private Category category;
	private Boolean active;

}
