package com.cwm.ecom.repo;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cwm.ecom.dao.ProductDao;
import com.cwm.ecom.model.Product;

@DataJpaTest
public class ProductDaoTest {

    @Autowired
    private ProductDao repo;

    private Product prod1, prod2;

    @BeforeEach
    public void setup() {
        prod1 = Product.builder().name("Crash Course in Python")
        		.description("Learn Python at your own pace. The author explains how the technology works in easy-to-understand language.")
        		.unitPrice(14.99)
        		.image("assets/images/products/books/book-luv2code-1000.png")
        		.unitsInStock(100)
        		.dateCreadted(new Date())
        		.lastUpdated(new Date())
        		.categeoryId(1L)
        		.sku("cwm")
        		.build();
        prod2 =  Product.builder().name("JavaScript Cookbook")
        		.description("Learn Javascript at your own pace. The author explains how the technology works in easy-to-understand language.")
        		.unitPrice(23.99)
        		.image("assets/images/products/books/book-luv2code-1005.png")
        		.unitsInStock(100)
        		.dateCreadted(new Date())
        		.lastUpdated(new Date())
        		.categeoryId(1L)
        		.sku("cwm")
        		.build();
        
    }

    @DisplayName("Repository Save product")
    @Test
    public void testSaveEntity() {
        Product savedProduct = repo.save(prod1);

        assertThat(savedProduct.getName()).isEqualTo(prod1.getName());
        assertThat(savedProduct.getUnitPrice()).isEqualTo(prod1.getUnitPrice());
        assertThat(savedProduct.getDescription()).isEqualTo(prod1.getDescription());
        assertThat(savedProduct.getUnitsInStock()).isEqualTo(prod1.getUnitsInStock());
        assertThat(savedProduct.getSku()).isEqualTo(prod1.getSku());
        assertThat(savedProduct.getCategeoryId()).isEqualTo(prod1.getCategeoryId());
    }

    @DisplayName("Repository find by id")
    @Test
    public void testFindById() {
        repo.save(prod1);
        Product product = repo.findById(prod1.getId()).orElseThrow();
        
        assertThat(product.getName()).isEqualTo(prod1.getName());
        assertThat(product.getUnitPrice()).isEqualTo(prod1.getUnitPrice());
        assertThat(product.getDescription()).isEqualTo(prod1.getDescription());
        assertThat(product.getUnitsInStock()).isEqualTo(prod1.getUnitsInStock());
        assertThat(product.getSku()).isEqualTo(prod1.getSku());
        assertThat(product.getCategeoryId()).isEqualTo(prod1.getCategeoryId());
    }

    @DisplayName("Repository find all")
    @Test
    public void testFindAll() {
        repo.save(prod1);
        repo.save(prod2);

        List<Product> products = repo.findAll();

        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("Crash Course in Python");
        assertThat(products.get(1).getName()).isEqualTo("JavaScript Cookbook");
    }

    @DisplayName("Repository delete by id")
    @Test
    public void testDeleteById() {
        repo.save(prod1);

        repo.deleteById(prod1.getId());

        Optional<Product> productFromDB = repo.findById(prod1.getId());
        assertThat(productFromDB).isEmpty();
    }

    @DisplayName("Repository update product")
    @Test
    public void testUpdateEntity() {
        repo.save(prod1);

        Product product = repo.findById(prod1.getId()).orElseThrow();
        product.setUnitPrice(17.29);
        product.setUnitsInStock(115);
        repo.save(product);

        Product updatedProduct = repo.findById(prod1.getId()).orElseThrow();

        assertThat(updatedProduct.getUnitsInStock()).isEqualTo(115);
        assertThat(updatedProduct.getUnitPrice()).isEqualTo(17.29);
    }
}
