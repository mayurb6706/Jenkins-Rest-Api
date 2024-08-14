package com.cwm.ecom.repo;
import static org.assertj.core.api.Assertions.assertThat;

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
        prod1 = Product.builder().name("Iphone 12").price(1250.0).quantity(10).image("iphone12.png").build();
        prod2 = Product.builder().name("Iphone 15").price(1550.0).quantity(10).image("iphone15.png").build();
    }

    @DisplayName("Repository Save product")
    @Test
    public void testSaveEntity() {
        Product savedProduct = repo.save(prod1);

        assertThat(savedProduct.getName()).isEqualTo(prod1.getName());
        assertThat(savedProduct.getPrice()).isEqualTo(prod1.getPrice());
    }

    @DisplayName("Repository find by id")
    @Test
    public void testFindById() {
        repo.save(prod1);
        Product product = repo.findById(prod1.getId()).orElseThrow();
        
        assertThat(product.getName()).isEqualTo("Iphone 12");
        assertThat(product.getPrice()).isEqualTo(1250.0);
    }

    @DisplayName("Repository find all")
    @Test
    public void testFindAll() {
        repo.save(prod1);
        repo.save(prod2);

        List<Product> products = repo.findAll();

        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("Iphone 12");
        assertThat(products.get(1).getName()).isEqualTo("Iphone 15");
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
        product.setPrice(1700.0);
        product.setQuantity(15);
        repo.save(product);

        Product updatedProduct = repo.findById(prod1.getId()).orElseThrow();

        assertThat(updatedProduct.getQuantity()).isEqualTo(15);
        assertThat(updatedProduct.getPrice()).isEqualTo(1700.0);
    }
}
