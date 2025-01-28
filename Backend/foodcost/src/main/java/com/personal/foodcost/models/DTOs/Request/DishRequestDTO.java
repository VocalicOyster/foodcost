package com.personal.foodcost.models.DTOs.Request;

import com.personal.foodcost.entities.RawMaterial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDTO {

    private String name;
    private List<Map<String, Float>> rowMaterialList;
    private Double price;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, Float>> getRowMaterialList() {
        return rowMaterialList;
    }

    public void setRowMaterialList(List<Map<String, Float>> rowMaterialList) {
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
