package com.personal.foodcost.validators;

import com.personal.foodcost.entities.Ingredient;
import com.personal.foodcost.entities.RawMaterial;
import com.personal.foodcost.exceptions.IngredientException;
import com.personal.foodcost.mappers.RawMaterialMapper;
import com.personal.foodcost.models.DTOs.request_dto.IngredientRequestDTO;
import com.personal.foodcost.models.DTOs.request_dto.RawMaterialRequestDTO;
import com.personal.foodcost.repositories.IngredientRepository;
import com.personal.foodcost.repositories.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class IngredientValidator {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private RawMaterialMapper rawMaterialMapper;

    public boolean isRawMaterialPerDishValid(IngredientRequestDTO ingredientRequestDTO) throws IngredientException {
        try {
            return isRawMaterialPerDishNotNull(ingredientRequestDTO) &&
                    isRawMaterialInDatabase(ingredientRequestDTO.getRawMaterial());
        } catch (IngredientException e) {
            throw new IngredientException(e.getMessage(), 400);
        }
    }

    private boolean isRawMaterialPerDishNotNull(IngredientRequestDTO ingredientRequestDTO) throws IngredientException {
        if (
                !Objects.equals(ingredientRequestDTO.getRawMaterial(), null) &&
                        !ingredientRequestDTO.getQuantity().isNaN()
        ) {
            return true;
        }
        throw new IngredientException("Something missing...", 400);
    }

    private boolean isRawMaterialInDatabase(RawMaterial rawMaterial) throws IngredientException {
        rawMaterialRepository.findById(rawMaterial.getId()).orElseThrow(() -> new IngredientException("Ingredient not present in database", 404));
        return true;
    }

}
