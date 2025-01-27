package com.personal.foodcost.models.DTOs.Request;

import com.personal.foodcost.entities.RawMaterial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDTO {

    private String name;
    private List<RawMaterial> rowMaterialList;
    private Double price;
    private String description;
}
