package com.hkbusiness.microserviceproduct.service;

import com.hkbusiness.microserviceproduct.dao.ProductDao;
import com.hkbusiness.microserviceproduct.exception.ProductNotFoundException;
import com.hkbusiness.microserviceproduct.model.Product;
import com.hkbusiness.microserviceproduct.model.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> fetchAllProducts() {
        return productDao.findAll();
    }

    @Override
    public Product findByProductCode(String productCode) throws ProductNotFoundException {
        Product product = productDao.findByProductCode(productCode);
        if (Objects.isNull(product)){
            throw new ProductNotFoundException("Product with code "+productCode+" is not found");
        }
        return product;
    }

    @Override
    public Product saveProduct(ProductDto productDto) {
        Product productToSave = new Product();
        productToSave.setProductName(productDto.getProductName());
        productToSave.setProductDescription(productDto.getProductDescription());
        productToSave.setProductMadeIn(productDto.getProductMadeIn());
        productToSave.setProductExpDate(productDto.getProductExpDate());
        productToSave.setProductManufDate(productDto.getProductManufDate());
        return productDao.save(productToSave);
    }

    @Override
    public Product updateProduct(@RequestBody Product product) {
        return productDao.save(product);
    }

    @Override
    public boolean deleteProductByCode(String productCode) {
        Optional<Product> productToDelete =  productDao.findAll().stream()
                .filter(product -> product.getProductCode().equals(productCode)).findFirst();
        if (productToDelete.isPresent()){
            productDao.deleteById(productCode);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(Product product) {
        Optional<Product> productToDelete =  productDao.findAll().stream()
                .filter(prod -> prod.getProductCode().equals(product.getProductCode())).findFirst();
        if (productToDelete.isPresent()){
            productDao.delete(product);
            return true;
        }
        return false;
    }
}
