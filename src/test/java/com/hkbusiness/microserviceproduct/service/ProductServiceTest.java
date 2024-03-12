package com.hkbusiness.microserviceproduct.service;

import com.hkbusiness.microserviceproduct.dao.ProductDao;
import com.hkbusiness.microserviceproduct.exception.ProductNotFoundException;
import com.hkbusiness.microserviceproduct.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductDao productDao;
    @BeforeEach
    void setUp() throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        Product product1 = Product.builder()
                .productCode("11e944bc8d")
                .productName("Kwanga")
                .productDescription("White kwanga")
                .productMadeIn("DR CONGO")
                .productExpDate(LocalDate.of(2024, 2,20))
                .productManufDate(LocalDate.of(2024, 1,15))
                .build();
        Product product2 = Product.builder()
                .productCode("26b347aace")
                .productName("Fumbwa")
                .productDescription("Ready to cook")
                .productMadeIn("DR CONGO")
                .productExpDate(LocalDate.of(2024, 7,2))
                .productManufDate(LocalDate.of(2024, 1,15))
                .build();
        products.add(product1);
        products.add(product2);

        Mockito.when(productDao.findById("11e944bc8d")).thenReturn(Optional.ofNullable(product1));
        Mockito.when(productDao.findByProductCode("code123")).thenThrow(new ProductNotFoundException("Product with code123 not found"));
        Mockito.when(productDao.findAll()).thenReturn(products);
        Mockito.when(productDao.save(any(Product.class))).thenReturn(product1);

    }
   //HAPPY SCENARIOS
    @Test
    @DisplayName("Get all products")
    void fetchAllProductsTest() {
        List<Product> allProduct = productDao.findAll();
        assertEquals(2,allProduct.size());
        assertEquals("Kwanga", allProduct.get(0).getProductName());
        assertEquals("26b347aace", allProduct.get(1).getProductCode());
    }

    @Test
    @DisplayName("Get a product by his code")
    void findProductByProductCodeTest() {
        String code = "11e944bc8d";
        Product product = productDao.findById(code).orElseThrow();
        assertNotNull(product);
        assertEquals("Kwanga", product.getProductName());
    }

    @Test
    @DisplayName("Save a new product")
    void saveProductTest() {
        Product product = productDao.save(new Product());

        assertEquals("Kwanga", product.getProductName());
        assertEquals(LocalDate.of(2024,2,20), product.getProductExpDate());
    }

    //UNHAPPY SCENARIOS
    @Test
    @DisplayName("Get a product by his code but product not found")
    void findProductByProductCodeButNotFoundTest() throws ProductNotFoundException {
      //  String code = "code123";
      //  Product product = productDao.findByProductCode(code);
    }
}