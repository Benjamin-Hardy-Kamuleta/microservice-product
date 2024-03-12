package com.hkbusiness.microserviceproduct.exception;

public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
