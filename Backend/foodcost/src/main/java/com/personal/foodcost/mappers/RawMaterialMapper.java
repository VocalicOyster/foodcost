package com.personal.foodcost.mappers;

import com.personal.foodcost.entities.RawMaterial;
import com.personal.foodcost.models.DTOs.request_dto.RawMaterialRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.RawMaterialResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RawMaterialMapper {

    public RawMaterial dtoToEntity(RawMaterialRequestDTO rawMaterialRequestDTO) {
        RawMaterial rawMaterial = new RawMaterial();

        rawMaterial.setId(rawMaterialRequestDTO.getId());
        rawMaterial.setPrice(rawMaterialRequestDTO.getPrice());
        rawMaterial.setName(rawMaterialRequestDTO.getName());
        rawMaterial.setVendor(rawMaterialRequestDTO.getVendor());
        rawMaterial.setDescription(rawMaterialRequestDTO.getDescription());
        rawMaterial.setQuantity(rawMaterialRequestDTO.getQuantity());

        return rawMaterial;
    }

    public RawMaterialResponseDTO entityToDto(RawMaterial rawMaterial) {
        RawMaterialResponseDTO rawMaterialResponseDTO = new RawMaterialResponseDTO();

        rawMaterialResponseDTO.setId(rawMaterial.getId());
        rawMaterialResponseDTO.setName(rawMaterial.getName());
        rawMaterialResponseDTO.setPrice(rawMaterial.getPrice());
        rawMaterialResponseDTO.setQuantity(rawMaterial.getQuantity());

        return rawMaterialResponseDTO;
    }
}
