package com.personal.foodcost.mappers;

import com.personal.foodcost.entities.Ingredient;
import com.personal.foodcost.models.DTOs.request_dto.IngredientRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.IngredientResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public Ingredient dtoToEntity(IngredientRequestDTO ingredientRequestDTO) {
        Ingredient ingredient = new Ingredient();

        ingredient.setRawMaterial(ingredientRequestDTO.getRawMaterial());
        ingredient.setId(ingredientRequestDTO.getId());
        ingredient.setQuantity(ingredientRequestDTO.getQuantity());

        return ingredient;
    }

    public IngredientResponseDTO entityToDto(Ingredient ingredient) {
        IngredientResponseDTO ingredientResponseDTO = new IngredientResponseDTO();

        ingredientResponseDTO.setId(ingredient.getId());
        ingredientResponseDTO.setQuantity(ingredient.getQuantity());

        return ingredientResponseDTO;
    }
}
