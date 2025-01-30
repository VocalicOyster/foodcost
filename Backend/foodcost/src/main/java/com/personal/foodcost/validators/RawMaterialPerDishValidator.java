package com.personal.foodcost.validators;

import com.personal.foodcost.exceptions.RawMaterialPerDishException;
import com.personal.foodcost.models.DTOs.request_dto.RawMaterialPerDishRequestDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RawMaterialPerDishValidator {

    public boolean isRawMaterialPerDishValid(RawMaterialPerDishRequestDTO rawMaterialPerDishRequestDTO) {

    }

    private boolean isRawMaterialPerDishNotNull(RawMaterialPerDishRequestDTO rawMaterialPerDishRequestDTO) throws RawMaterialPerDishException {
        if(
                !Objects.equals(rawMaterialPerDishRequestDTO.getName(), "") &&
                        !rawMaterialPerDishRequestDTO.getPrice().isNaN() &&
                        !rawMaterialPerDishRequestDTO.getQuantity().isNaN() &&
                        rawMaterial
        ) {
            return true;
        }
        throw new RawMaterialPerDishException("Something missing...", 400);
    }
}
