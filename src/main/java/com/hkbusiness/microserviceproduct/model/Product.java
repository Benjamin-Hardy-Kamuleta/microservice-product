package com.hkbusiness.microserviceproduct.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @NotNull(message = "provide a notNull productExpDate in format yyyy-MM-dd ")
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
