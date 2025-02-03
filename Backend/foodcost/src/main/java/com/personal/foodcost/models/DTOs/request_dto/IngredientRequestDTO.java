package com.personal.foodcost.models.DTOs.request_dto;

import com.personal.foodcost.entities.RawMaterial;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRequestDTO {

    private Long id;
    private RawMaterial rawMaterial;
    private Float quantity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }
}
