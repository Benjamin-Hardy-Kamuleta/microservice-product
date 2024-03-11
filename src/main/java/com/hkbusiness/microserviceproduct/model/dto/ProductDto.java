package com.hkbusiness.microserviceproduct.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductDto {

    private String productName;
    private String productDescription;
    private String productMadeIn;
    private LocalDate productExpDate;
    private LocalDate productManufDate;
}
