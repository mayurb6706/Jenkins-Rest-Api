
package com.cwm.product.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cwm.product.entity.ProductRequest;
import com.cwm.product.entity.ProductResponse;
import com.cwm.product.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	private static final String BASE_URL = "/api/product";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductServiceImpl productServiceImpl;

	@Autowired
	private ObjectMapper mapper;

	private ProductRequest productRequest;
	private ProductResponse productResponse;

	@BeforeEach
	public void setup() {
		productRequest = new ProductRequest();
		productRequest.setId(1L);
		productRequest.setName("Iphone 12");
		productRequest.setPrice(1250.0);
		productRequest.setQuantity(10);
		productRequest.setImage("iphone12.png");

		productResponse = ProductResponse.builder().id(1L).name("Iphone 12").price(1250.0).quantity(10)
				.image("iphone12.png").build();

	}

	@DisplayName("Controller save product")
	@Test
	public void testAddProduct() throws Exception {

		given(productServiceImpl.addProduct(any(ProductRequest.class))).willReturn(productResponse);

		// Perform the request
		ResultActions resultActions = this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(productRequest))).andDo(print());

		// Verify the results
		resultActions.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Iphone 12")))
				.andExpect(jsonPath("$.price", is(1250.0)));

	}

	@DisplayName("Controller get single product")
	@Test 
	public void testGetProductById() throws Exception{
		when(productServiceImpl.getProductById(anyLong())).thenReturn(productResponse);
		ResultActions resultActions= mockMvc.perform(get(BASE_URL+"/{id}",1L).contentType(MediaType.APPLICATION_JSON));
		
		resultActions.andDo(print()).andExpect(status().isOk());
		
		verify(productServiceImpl , times(1)).getProductById(anyLong());
	}

	@DisplayName("Controller get all products")
	@Test
	public void testGetAllProduct() throws Exception {
		ProductResponse productResponse2 = ProductResponse.builder().id(2L).name("Iphone 15").price(1550.0).quantity(10)
				.image("iphone15.png").build();

		List<ProductResponse> productResponses = Arrays.asList(productResponse, productResponse2);
		given(productServiceImpl.getAllProduct()).willReturn(productResponses);

		ResultActions resultActions = mockMvc.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON));

		resultActions.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@DisplayName("Controller update product")
	@Test
	public void testUpdateProduct() throws Exception {
		ProductResponse updatedProductResponse = ProductResponse.builder().id(1L).name("Iphone 12").price(1550.0).quantity(15)
				.image("iphone12.png").build();
		ProductRequest updatedProductRequest = ProductRequest.builder().id(1L).name("Iphone 12").price(1550.0).quantity(15)
				.image("iphone12.png").build();


		given(productServiceImpl.updateProduct(anyLong(), any(ProductRequest.class))).willReturn(updatedProductResponse);
		
		ResultActions resultActions= mockMvc.perform(put(BASE_URL+"/{id}",1L).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(updatedProductRequest))).andDo(print());
		
		resultActions.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Iphone 12")))
		.andExpect(jsonPath("$.price", is(1550.0)));
	}
}
