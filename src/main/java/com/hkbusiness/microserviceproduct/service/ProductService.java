package com.hkbusiness.microserviceproduct.service;

import com.hkbusiness.microserviceproduct.model.Product;
import com.hkbusiness.microserviceproduct.model.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    List<Product> fetchAllProducts();
    Product findByProductCode(String productCode);
    Product saveProduct(ProductDto productDto);
    Product updateProduct(Product product);
    boolean deleteProductByCode(String productCode);
    boolean deleteProduct(Product product);
}
