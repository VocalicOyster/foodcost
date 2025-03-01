package com.personal.foodcost.validators;

import com.personal.foodcost.entities.RawMaterial;
import com.personal.foodcost.exceptions.RawMaterialException;
import com.personal.foodcost.models.DTOs.request_dto.RawMaterialRequestDTO;
import com.personal.foodcost.repositories.RawMaterialRepository;
import com.personal.foodcost.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RawMaterialValidator {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private JwtService jwtService;

    public boolean isRawMaterialValid(RawMaterialRequestDTO rawMaterialRequestDTO, String jwt) throws RawMaterialException {

        String restName = jwtService.extractRestName(jwt);

        try {
            return isRawMaterialNotNull(rawMaterialRequestDTO) &&
                    isNotInDatabase(rawMaterialRequestDTO, restName);
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

    private boolean isNotInDatabase(RawMaterialRequestDTO rawMaterialRequestDTO, String restName) throws RawMaterialException {
        RawMaterial rawMaterial = rawMaterialRepository.findByName(rawMaterialRequestDTO.getName(), restName).orElse(null);
        if(rawMaterial == null) {
            return true;
        } else throw new RawMaterialException("This Raw Material is already in database", 400);
    }
}
