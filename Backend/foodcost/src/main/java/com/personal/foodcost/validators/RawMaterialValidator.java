package com.personal.foodcost.validators;

import com.personal.foodcost.entities.RawMaterial;
import com.personal.foodcost.models.DTOs.Request.RawMaterialRequestDTO;
import com.personal.foodcost.repositories.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RawMaterialValidator {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public boolean isRawMaterialValid(RawMaterialRequestDTO rawMaterialRequestDTO) {
        return isRawMaterialNotNull(rawMaterialRequestDTO) &&
                isNotInDatabase(rawMaterialRequestDTO);
    }

    private boolean isRawMaterialNotNull(RawMaterialRequestDTO rawMaterialRequestDTO) {
        return rawMaterialRequestDTO.getQuantity() != null &&
                rawMaterialRequestDTO.getVendor() != null &&
                rawMaterialRequestDTO.getPrice() != null &&
                rawMaterialRequestDTO.getName() != null;
    }

    private boolean isNotInDatabase(RawMaterialRequestDTO rawMaterialRequestDTO) {
        RawMaterial rawMaterial = rawMaterialRepository.findByName(rawMaterialRequestDTO.getName()).orElse(null);
        return rawMaterial == null;
    }
}
