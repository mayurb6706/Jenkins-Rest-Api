package com.cwm.ecom.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
        		.image("assets/images/products/books/book-luv2code-1000.png")
        		.unitsInStock(100)
        		.dateCreadted(new Date())
        		.lastUpdated(new Date())
        		.categeoryId(1L)
        		.sku("cwm")
        		.build();
		
		productResponse= new ProductResponse();
		productResponse.setCategeoryId(1L);
		productResponse.setName("Crash Course in Python");
		productResponse.setDescription("Learn Python at your own pace. The author explains how the technology works in easy-to-understand language.");
		productResponse.setUnitPrice(14.99);
		productResponse.setImage("assets/images/products/books/book-luv2code-1000.png");
		productResponse.setUnitsInStock(100);
		productResponse.setDateCreadted(new Date());
		productResponse.setLastUpdated(new Date());
		productResponse.setSku("CWM");
	}
	
	@Test
	public void testAddProduct()  throws Exception{
		BDDMockito.given(productService.addProduct(productRequest)).willReturn(productResponse);
		
		ResultActions resultActions= mockMvc.perform(post(BASE_URL,productRequest).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(productRequest)));
		
		resultActions.andDo(print());
	}
}
