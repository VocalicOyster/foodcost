package com.personal.foodcost.models.DTOs.Request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialRequestDTO {

    private String name;

    //in Kg
    private Integer quantity;

    private Double price;
    private String vendor;
    private String description;


}
