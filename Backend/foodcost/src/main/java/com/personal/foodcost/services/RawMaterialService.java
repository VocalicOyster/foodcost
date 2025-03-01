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

    @Autowired
    private JwtService jwtService;

    public List<RawMaterialResponseDTO> getAllRaw(String jwt) {
        String restName = jwtService.extractRestName(jwt);
        List<RawMaterial> rawMaterialList = rawMaterialRepository.findAll(restName);
        List<RawMaterialResponseDTO> rawMaterialResponseDTOList = new ArrayList<>();

        for(RawMaterial rawMaterial: rawMaterialList) {
            rawMaterialResponseDTOList.add(rawMaterialMapper.entityToDto(rawMaterial));
        }

        return rawMaterialResponseDTOList;
    }

    public RawMaterialResponseDTO getRawById(Long id, String jwt) throws RawMaterialException {
        String restName = jwtService.extractRestName(jwt);
        RawMaterial rawMaterial = rawMaterialRepository.findById(id, restName).orElseThrow(() -> new RawMaterialException("Raw material not found with this id", 404));

        return rawMaterialMapper.entityToDto(rawMaterial);
    }


    public RawMaterialResponseDTO insertRaw(RawMaterialRequestDTO rawMaterialRequestDTO, String jwt) throws RawMaterialException {
        String restName = jwtService.extractRestName(jwt);
        try {
            rawMaterialValidator.isRawMaterialValid(rawMaterialRequestDTO, restName);
            RawMaterial rawMaterial = rawMaterialMapper.dtoToEntity(rawMaterialRequestDTO);
            rawMaterial.setRestName(restName);
            RawMaterial saved = rawMaterialRepository.saveAndFlush(rawMaterial);
            return rawMaterialMapper.entityToDto(saved);
        } catch (RawMaterialException e) {
            throw new RawMaterialException(e.getMessage(), e.getStatusCode());
        }

    }

    public RawMaterialResponseDTO updateById(Long id, RawMaterialRequestDTO rawMaterialRequestDTO, String jwt) throws RawMaterialException {

        String restName = jwtService.extractRestName(jwt);
        try {
            rawMaterialValidator.isRawMaterialValid(rawMaterialRequestDTO, restName);
            RawMaterial rawMaterial = rawMaterialRepository.findById(id, restName).orElseThrow(() -> new RawMaterialException("No raw material found with this id", 404));

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

    public RawMaterialResponseDTO deleteById(Long id, String jwt) throws RawMaterialException {
        String restName = jwtService.extractRestName(jwt);
        RawMaterial rawMaterial = rawMaterialRepository.findById(id, restName).orElseThrow(() -> new RawMaterialException("No Raw Material found with this id!", 404));

        rawMaterialRepository.deleteById(id);

        return rawMaterialMapper.entityToDto(rawMaterial);
    }

}
