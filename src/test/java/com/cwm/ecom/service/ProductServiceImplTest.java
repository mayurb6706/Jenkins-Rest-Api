
package com.cwm.ecom.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cwm.ecom.dao.ProductDao;
import com.cwm.ecom.entity.ProductRequest;
import com.cwm.ecom.entity.ProductResponse;
import com.cwm.ecom.exception.ProductNotFoundException;
import com.cwm.ecom.model.Category;
import com.cwm.ecom.model.Product;
import com.cwm.ecom.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest productRequest;
    private Category category;

    @BeforeEach
    public void setup() {
    	category = Category.builder()
    			.id(1L)
    			.name("Books")
    			.build();
        product = Product.builder().name("Crash Course in Python")
        		.description("Learn Python at your own pace. The author explains how the technology works in easy-to-understand language.")
        		.unitPrice(14.99)
        		.imageUrl("assets/images/products/books/book-luv2code-1000.png")
        		.unitsInStock(100)
        		.active(true)
        		.dateCreated(new Date())
        		.category(category)
        		.sku("cwm")
        		.build();

        productRequest = new ProductRequest();
        productRequest.setName("Crash Course in Python");
        productRequest.setUnitPrice(14.99);
        productRequest.setUnitsInStock(100);
        productRequest.setImageUrl("assets/images/products/books/book-luv2code-1000.png");
        productRequest.setCategory(category);
        productRequest.setCategory(category);
        productRequest.setSku("cwm");
        productRequest.setDateCreadted(new Date());
    }

    @Test
    public void testAddProduct() {
        when(productDao.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.addProduct(productRequest);

        assertThat(response).isNotNull();
        System.out.println();
        assertThat(response.getName()).isEqualTo("Crash Course in Python");
        assertThat(response.getUnitPrice()).isEqualTo(14.99);
        assertThat(response.getUnitsInStock()).isEqualTo(100);
        assertThat(response.getImageUrl()).isEqualTo("assets/images/products/books/book-luv2code-1000.png");
        assertThat(response.getSku()).isEqualTo("cwm");
        verify(productDao, times(1)).save(any(Product.class));
    }

    @Test
    public void testGetAllProduct() {
        when(productDao.findAll()).thenReturn(Arrays.asList(product));

        List<ProductResponse> responses = productService.getAllProduct();

        assertThat(responses).isNotEmpty();
        assertThat(responses.size()).isEqualTo(1);
        assertThat(responses.get(0).getName()).isEqualTo("Crash Course in Python");
        verify(productDao, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        when(productDao.findById(anyLong())).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertThat(response).isNotNull();
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Crash Course in Python");
        assertThat(response.getUnitPrice()).isEqualTo(14.99);
        assertThat(response.getUnitsInStock()).isEqualTo(100);
        assertThat(response.getImageUrl()).isEqualTo("assets/images/products/books/book-luv2code-1000.png");
         assertThat(response.getSku()).isEqualTo("cwm");
        verify(productDao, times(1)).findById(anyLong());
    }

    @Test
    public void testGetProductByIdThrowsException() {
        when(productDao.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(1L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product not exit with id 1");

        verify(productDao, times(1)).findById(anyLong());
    }
    
    @Test 
    public void testUpdateProduct() {
 
    	ProductRequest updatedProduct=ProductRequest.builder()
    			.name("Crash Course in Python2")
        		.description("Learn Python at your own pace. The author explains how the technology works in easy-to-understand language.")
        		.unitPrice(17.35)
        		.imageUrl("assets/images/products/books/book-luv2code-1001.png")
        		.unitsInStock(115)
        		.dateCreadted(new Date())
        		.category(category)
        		.sku("cwm")
        		.active(true)
        		.build();
    	when(productDao.findById(anyLong())).thenReturn(Optional.of(product));
    	when(productDao.save(any(Product.class))).thenReturn(product);
    	
    	Long productId= 1L;
    	
    	ProductResponse response= productService.updateProduct(productId, updatedProduct);
    	
    	 assertThat(response).isNotNull();
         assertThat(response.getName()).isEqualTo("Crash Course in Python2");
         assertThat(response.getUnitPrice()).isEqualTo(17.35);
         assertThat(response.getUnitsInStock()).isEqualTo(115);
         assertThat(response.getImageUrl()).isEqualTo("assets/images/products/books/book-luv2code-1001.png");
         assertThat(response.getSku()).isEqualTo("cwm");
         verify(productDao, times(1)).findById(anyLong());
         verify(productDao, times(1)).save(any(Product.class));
    }
    
    @Test
    public void testDeleteProduct() {
    	
    	when(productDao.findById(1L)).thenReturn(Optional.of(product));
    	
    	willDoNothing().given(productDao).delete(product);
    	
    	productService.deleteProduct(1L);

    	verify(productDao,times(1)).findById(anyLong());
    	verify(productDao , times(1)).delete(product);
    }
    
    @Test
    public void testDeleteProductWithNotFoundException() {
    	
    	Long productId= 1L;
    	when(productDao.findById(anyLong())).thenReturn(Optional.empty());
 
    	 Exception exception = assertThrows(ProductNotFoundException.class, () -> {
             productService.deleteProduct(productId);
         });
    	 
    	 assertEquals("Product not exit with id "+productId, exception.getLocalizedMessage());
    	verify(productDao, times(1)).findById(productId);
    	
    	}
}

