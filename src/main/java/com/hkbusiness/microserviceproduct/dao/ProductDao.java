package com.hkbusiness.microserviceproduct.dao;

import com.hkbusiness.microserviceproduct.model.Product;
import com.hkbusiness.microserviceproduct.model.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, String> {
    List<Product> fetchAllProducts();
    Product findByProductCode(String productCode);
    Product saveProduct(ProductDto productDto);
    Product updateProduct(Product product);

    boolean deleteProductByCode(String productCode);
    boolean deleteProduct(Product product);
}
