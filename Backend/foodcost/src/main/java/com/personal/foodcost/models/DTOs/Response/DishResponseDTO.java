package com.personal.foodcost.models.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishResponseDTO {

    private String name;
    private Double price;
    private String description;

}
