package com.cwm.ecom.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cwm.ecom.entity.ProductRequest;
import com.cwm.ecom.entity.ProductResponse;
import com.cwm.ecom.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

//@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	private static final String BASE_URL="/api/product";
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper=new ObjectMapper();
	
	ProductResponse productResponse;
	ProductRequest productRequest;
	
	@Mock
	private ProductServiceImpl productService;
	
	@InjectMocks
	private ProductController productController;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

		productRequest=ProductRequest.builder()
				.name("Crash Course in Python")
        		.description("Learn Python at your own pace. The author explains how the technology works in easy-to-understand language.")
        		.unitPrice(14.99)
        		.imageUrl("assets/images/products/books/book-luv2code-1000.png")
        		.unitsInStock(100)
        		.dateCreadted(new Date())
        		.lastUpdated(new Date())
        		.active(true)
        		.category(1L)
        		.sku("cwm")
        		.build();
		
		productResponse= new ProductResponse();
		productResponse.setCategory(1L);
		productResponse.setName("Crash Course in Python");
		productResponse.setDescription("Learn Python at your own pace. The author explains how the technology works in easy-to-understand language.");
		productResponse.setUnitPrice(14.99);
		productResponse.setImageUrl("assets/images/products/books/book-luv2code-1000.png");
		productResponse.setUnitsInStock(100);
		productResponse.setDateCreadted(new Date());
		productResponse.setLastUpdated(new Date());
		productResponse.setActive(true);
		productResponse.setSku("CWM");
	}
	
	@Test
	public void testAddProduct()  throws Exception{
		given(productService.addProduct(productRequest)).willReturn(productResponse);
		
		ResultActions resultActions= mockMvc.perform(post(BASE_URL,productRequest).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(productRequest)));
		
		resultActions.andDo(print());
	}
	
	@Test
	public void testGetProductById()  throws Exception{
		Long productId=1L;
		
		given(productService.getProductById(anyLong())).willReturn(productResponse);
		
		
		ResultActions resultActions= mockMvc.perform(post(BASE_URL+"/{id}",productId));
		
		resultActions.andDo(print());
		
	}
	
	@Test
	public void testGetAllProduct() throws Exception {
		List<ProductResponse> productResponses= new ArrayList<>();
		productResponses.add(productResponse);
		
		given(productService.getAllProduct()).willReturn(productResponses);
		
		ResultActions resultActions= this.mockMvc.perform(get(BASE_URL));
		resultActions.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteProduct() throws Exception{
		Long productId=1L;
		
		willDoNothing().given(productService).deleteProduct(anyLong());
		
		mockMvc.perform(delete(BASE_URL+"/{id}",productId)).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$", is("Deleted")));
		
		verify(productService, times(1)).deleteProduct(anyLong());

	}
	
	@Test
	public void testUpdteProduct() throws Exception {
		// Given
        Long productId = 1L;
        ProductRequest productRequest = ProductRequest.builder()
				.name("Crash Course in Python")
        		.description("Learn Python at your own pace. The author explains how the technology works in easy-to-understand language.")
        		.unitPrice(14.99)
        		.imageUrl("assets/images/products/books/book-luv2code-1000.png")
        		.unitsInStock(100)
        		.dateCreadted(new Date())
        		.lastUpdated(new Date())
        		.category(1L)
        		.sku("cwm")
        		.build();
        // Set fields on productRequest as needed

        ProductResponse productResponse = ProductResponse.builder()
				.name("Crash Course in Python")
        		.description("Learn Python at your own pace. The author explains how the technology works in easy-to-understand language.")
        		.unitPrice(14.99)
        		.imageUrl("assets/images/products/books/book-luv2code-1000.png")
        		.unitsInStock(100)
        		.dateCreadted(new Date())
        		.lastUpdated(new Date())
        		.category(1L)
        		.sku("cwm")
        		.build();
        // Set fields on productResponse as needed

        when(productService.updateProduct(eq(productId), any(ProductRequest.class)))
            .thenReturn(productResponse);

        // When & Then
        mockMvc.perform(put(BASE_URL+"/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(productRequest)))
            .andExpect(status().isOk());
   
		
	}
	
}
