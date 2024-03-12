package com.hkbusiness.microserviceproduct.controller;

import com.hkbusiness.microserviceproduct.exception.ProductNotFoundException;
import com.hkbusiness.microserviceproduct.model.Product;
import com.hkbusiness.microserviceproduct.model.dto.ProductDto;
import com.hkbusiness.microserviceproduct.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/")
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @Operation(tags = "Api Root")
    @GetMapping
    public void redirect(HttpServletResponse response) throws IOException {
        logger.info("redirecting to swagger");
        response.sendRedirect("/swagger-ui.html");
    }

    @Operation(
            summary = "Retrieve all products",
            description = "Get the list of all products",
            tags = { "All products"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @GetMapping("/products")
    public List<Product> products(){
        logger.info("fetching all products");
        return productService.fetchAllProducts();
    }
    @Operation(
            summary = "Retrieve a product by code",
            description = "Get product object by specifying his code",
            tags = { "Get a product"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "404", description = "Product not found",content = { @Content(schema = @Schema()) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @GetMapping("/products/{productCode}")
    public Product findProductByCode(@PathVariable String productCode) throws ProductNotFoundException {
        logger.info("fetching product with code : {}", productCode);
        return productService.findByProductCode(productCode);
    }
    @Operation(
            summary = "Add a product to database",
            description = "Add a product to database by specifying productDto object. The Response is a product object",
            tags = { "Add a product"})
    @ApiResponse(responseCode = "201", description = "new product created", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "400", description = "Bad request: check productDto object",content = { @Content(schema = @Schema()) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @PostMapping("/products")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDto productDto){
        logger.info("saving new product: {}", productDto);
        Product productAdded =  productService.saveProduct(productDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{productCode}")
                .buildAndExpand(productAdded.getProductCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Update an existing product",
            description = "Update an existing product, if the provided product code is not found then this " +
                    "product will be added as new product",
            tags = { "Edit a product"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "400", description = "Bad request: check product object", content = { @Content(schema = @Schema()) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @PutMapping("/products")
    public Product updateProduct(@RequestBody Product product){
        logger.info("updating product: {}", product);
        return productService.updateProduct(product);
    }

    @Operation(
            summary = "Delete a product by code",
            description = "Delete a product object by specifying his code. The response is true if the product is found " +
                    "and deleted, otherwise the response will be false",
            tags = { "Delete product by code"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "202", description = "The operation is accepted but the result will be false", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @DeleteMapping("/products/code/{productCode}")
    public ResponseEntity<Boolean> deleteProductByCode(@PathVariable String productCode){
        logger.info("deleting product with code: {}", productCode);
        if (productService.deleteProductByCode(productCode)){
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        logger.info("Product with code: {} is not found to be deleted", productCode);
        return ResponseEntity.accepted().body(Boolean.FALSE);
    }

    @Operation(
            summary = "Delete a product",
            description = "Delete a product object by specifying the object. " +
                    "The response is true if the product is found " +
                    "and deleted, otherwise the response will be false",
            tags = { "Delete product"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "202", description = "The operation is accepted but the result will be false", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @DeleteMapping("/products")
    public ResponseEntity<Boolean> deleteProduct(@RequestBody Product product){
        logger.info("deleting product: {}", product);
        logger.info("checking product code");
        if (productService.deleteProduct(product)){
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        logger.info("Product with code: {} is not found to be deleted", product.getProductCode());
        return ResponseEntity.accepted().body(Boolean.FALSE);
    }
}
