
package com.cwm.product.service;
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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cwm.product.dao.ProductDao;
import com.cwm.product.entity.ProductRequest;
import com.cwm.product.entity.ProductResponse;
import com.cwm.product.exception.ProductNotFoundException;
import com.cwm.product.model.Product;
import com.cwm.product.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    public void setup() {
        product = Product.builder()
                .id(1L)
                .name("Iphone 12")
                .price(1250.0)
                .quantity(10)
                .image("iphone12.png")
                .build();

        productRequest = new ProductRequest();
        productRequest.setName("Iphone 12");
        productRequest.setPrice(1250.0);
        productRequest.setQuantity(10);
        productRequest.setImage("iphone12.png");
    }

    @Test
    public void testAddProduct() {
        when(productDao.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.addProduct(productRequest);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Iphone 12");
        assertThat(response.getPrice()).isEqualTo(1250.0);
        assertThat(response.getQuantity()).isEqualTo(10);
        assertThat(response.getImage()).isEqualTo("iphone12.png");
        verify(productDao, times(1)).save(any(Product.class));
    }

    @Test
    public void testGetAllProduct() {
        when(productDao.findAll()).thenReturn(Arrays.asList(product));

        List<ProductResponse> responses = productService.getAllProduct();

        assertThat(responses).isNotEmpty();
        assertThat(responses.size()).isEqualTo(1);
        assertThat(responses.get(0).getName()).isEqualTo("Iphone 12");
        verify(productDao, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        when(productDao.findById(anyLong())).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Iphone 12");
        assertThat(response.getPrice()).isEqualTo(1250.0);
        assertThat(response.getQuantity()).isEqualTo(10);
        assertThat(response.getImage()).isEqualTo("iphone12.png");
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
                .id(1L)
                .name("Iphone 12")
                .price(1550.0)
                .quantity(15)
                .image("iphone12.png")
                .build();
    	when(productDao.findById(anyLong())).thenReturn(Optional.of(product));
    	when(productDao.save(any(Product.class))).thenReturn(product);
    	
    	
    	ProductResponse response= productService.updateProduct(product.getId(), updatedProduct);
    	
    	System.out.println(product);
    	 assertThat(response).isNotNull();
         assertThat(response.getName()).isEqualTo("Iphone 12");
         assertThat(response.getPrice()).isEqualTo(1550.0);
         assertThat(response.getQuantity()).isEqualTo(15);
         assertThat(response.getImage()).isEqualTo("iphone12.png");
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
//    	ProductNotFoundException exception = new ProductNotFoundException("Product not found.");
 
    	 Exception exception = assertThrows(ProductNotFoundException.class, () -> {
             productService.deleteProduct(productId);
         });
    	 
    	 assertEquals("Product not exit with id "+productId, exception.getLocalizedMessage());
    	verify(productDao, times(1)).findById(productId);
    	
    	}
}

