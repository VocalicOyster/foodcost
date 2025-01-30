package com.personal.foodcost.validators;

import com.personal.foodcost.entities.RawMaterial;
import com.personal.foodcost.exceptions.RawMaterialException;
import com.personal.foodcost.models.DTOs.request_dto.RawMaterialRequestDTO;
import com.personal.foodcost.repositories.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RawMaterialValidator {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public boolean isRawMaterialValid(RawMaterialRequestDTO rawMaterialRequestDTO) throws RawMaterialException {
        try {
            return isRawMaterialNotNull(rawMaterialRequestDTO) &&
                    isNotInDatabase(rawMaterialRequestDTO);
        }
        catch (RawMaterialException e) {
            throw new RawMaterialException(e.getMessage(), 400);
        }
    }

    private boolean isRawMaterialNotNull(RawMaterialRequestDTO rawMaterialRequestDTO) throws RawMaterialException {
        if(rawMaterialRequestDTO.getQuantity() != null &&
                !Objects.equals(rawMaterialRequestDTO.getVendor(), "null") &&
                rawMaterialRequestDTO.getPrice() != null &&
                !Objects.equals(rawMaterialRequestDTO.getName(), "")
        ) {
            return true;
        } else throw new RawMaterialException("Something missing...", 400);
    }

    private boolean isNotInDatabase(RawMaterialRequestDTO rawMaterialRequestDTO) throws RawMaterialException {
        RawMaterial rawMaterial = rawMaterialRepository.findByName(rawMaterialRequestDTO.getName()).orElse(null);
        if(rawMaterial == null) {
            return true;
        } else throw new RawMaterialException("This Raw Material is already in database", 400);
    }
}
