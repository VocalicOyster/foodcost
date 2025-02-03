package com.personal.foodcost.services;

import com.personal.foodcost.controllers.RawMaterialController;
import com.personal.foodcost.entities.RawMaterial;
import com.personal.foodcost.exceptions.RawMaterialException;
import com.personal.foodcost.mappers.RawMaterialMapper;
import com.personal.foodcost.models.DTOs.request_dto.RawMaterialRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.RawMaterialResponseDTO;
import com.personal.foodcost.repositories.RawMaterialRepository;
import com.personal.foodcost.validators.RawMaterialValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RawMaterialService {

    @Autowired
    private RawMaterialValidator rawMaterialValidator;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private RawMaterialMapper rawMaterialMapper;

    public List<RawMaterialResponseDTO> getAllRaw() {
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll();
        List<RawMaterialResponseDTO> rawMaterialResponseDTOList = new ArrayList<>();

        for(RawMaterial rawMaterial: rawMaterialList) {
            rawMaterialResponseDTOList.add(rawMaterialMapper.entityToDto(rawMaterial));
        }

        return rawMaterialResponseDTOList;
    }

    public RawMaterialResponseDTO getRawById(Long id) throws RawMaterialException {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new RawMaterialException("Raw material not found with this id", 404));

        return rawMaterialMapper.entityToDto(rawMaterial);
    }

    public RawMaterialResponseDTO insertRaw(RawMaterialRequestDTO rawMaterialRequestDTO) throws RawMaterialException {

        try {
            rawMaterialValidator.isRawMaterialValid(rawMaterialRequestDTO);
            RawMaterial rawMaterial = rawMaterialMapper.dtoToEntity(rawMaterialRequestDTO);
            RawMaterial saved = rawMaterialRepository.saveAndFlush(rawMaterial);
            return rawMaterialMapper.entityToDto(saved);
        } catch (RawMaterialException e) {
            throw new RawMaterialException(e.getMessage(), e.getStatusCode());
        }

    }

    public RawMaterialResponseDTO updateById(Long id, RawMaterialRequestDTO rawMaterialRequestDTO) throws RawMaterialException {
        try {
            rawMaterialValidator.isRawMaterialValid(rawMaterialRequestDTO);
            RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new RawMaterialException("No raw material found with this id", 404));

            rawMaterial.setName(rawMaterialRequestDTO.getName());
            rawMaterial.setVendor(rawMaterialRequestDTO.getVendor());
            rawMaterial.setDescription(rawMaterialRequestDTO.getDescription());
            rawMaterial.setPrice(rawMaterialRequestDTO.getPrice());

            RawMaterial saved = rawMaterialRepository.saveAndFlush(rawMaterial);

            return rawMaterialMapper.entityToDto(saved);
        } catch (RawMaterialException e) {
            throw new RawMaterialException(e.getMessage(), e.getStatusCode());
        }
    }

    public RawMaterialResponseDTO deleteById(Long id) throws RawMaterialException {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new RawMaterialException("No Raw Material found with this id!", 404));

        rawMaterialRepository.deleteById(id);

        return rawMaterialMapper.entityToDto(rawMaterial);
    }

}
