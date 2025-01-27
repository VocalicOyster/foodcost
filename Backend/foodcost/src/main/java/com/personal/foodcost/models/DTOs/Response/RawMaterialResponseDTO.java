package com.personal.foodcost.models.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialResponseDTO {


    private String name;

    //in Kg
    private Integer quantity;
    private Double price;
}
