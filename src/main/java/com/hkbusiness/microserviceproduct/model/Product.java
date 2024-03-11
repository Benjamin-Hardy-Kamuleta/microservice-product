package com.hkbusiness.microserviceproduct.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    private String productCode = makeProductCode();
    @NotBlank(message = "productName cannot be blank")
    private String productName;
    @NotBlank(message = "productDescription cannot be blank")
    private String productDescription;
    @NotBlank(message = "productMadeIn cannot be blank")
    private String productMadeIn;
    @NotBlank(message = "please provide productExpDate ")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyy-MM-dd")
    private LocalDate productExpDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyy-MM-dd")
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
