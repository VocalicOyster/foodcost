package com.personal.foodcost.models.DTOs.response_dto;

import com.personal.foodcost.entities.RawMaterial;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialResponseDTO {

    private Long id;
    private String name;

    //in Kg
    private Float quantity;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}