package com.hkbusiness.microserviceproduct.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    private String productCode = makeProductCode();
    private String productName;
    private String productDescription;
    private String productMadeIn;
    private LocalDate productExpDate;
    private LocalDate productManufDate;
    private String makeProductCode(){
        StringBuilder code= new StringBuilder();
        String[] parts = UUID.randomUUID().toString().split("-");
        for (String part : parts){
            code.append(part, 0, 2);
        }
        return code.toString();
    }

}
