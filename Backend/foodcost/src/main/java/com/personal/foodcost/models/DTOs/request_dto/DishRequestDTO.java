package com.personal.foodcost.models.DTOs.request_dto;

import com.personal.foodcost.entities.Ingredient;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDTO {

    private Long id;
    private String name;
    private String description;
    private List<IngredientRequestDTO> ingredientList;
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

    public List<IngredientRequestDTO> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientRequestDTO> ingredientList) {
        this.ingredientList = ingredientList;
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
