package com.hkbusiness.microserviceproduct.dao;

import com.hkbusiness.microserviceproduct.exception.ProductNotFoundException;
import com.hkbusiness.microserviceproduct.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, String> {
    Product findByProductCode(String productCode) throws ProductNotFoundException;
}
