package com.personal.foodcost.models.DTOs.request_dto;

import com.personal.foodcost.entities.RawMaterialPerDish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDTO {

    private Long id;
    private String name;
    private List<RawMaterialPerDish> rowMaterialList;
    private Double price;
    private String description;

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

    public List<RawMaterialPerDish> getRowMaterialList() {
        return rowMaterialList;
    }

    public void setRowMaterialList(List<RawMaterialPerDish> rowMaterialList) {
        this.rowMaterialList = rowMaterialList;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
