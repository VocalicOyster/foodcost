package com.personal.foodcost.services;

import com.personal.foodcost.entities.Ingredient;
import com.personal.foodcost.exceptions.IngredientException;
import com.personal.foodcost.mappers.IngredientMapper;
import com.personal.foodcost.models.DTOs.request_dto.IngredientRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.IngredientResponseDTO;
import com.personal.foodcost.repositories.IngredientRepository;
import com.personal.foodcost.validators.IngredientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private IngredientValidator ingredientValidator;

    @Autowired
    private JwtService jwtService;

    public List<IngredientResponseDTO> getAllIngredients(String jwt) {
        String restName = jwtService.extractRestName(jwt);
        List<Ingredient> ingredientList = ingredientRepository.findAll(restName);
        List<IngredientResponseDTO> ingredientResponseDTOList = new ArrayList<>();

        for(Ingredient ingredient: ingredientList) {
            ingredientResponseDTOList.add(ingredientMapper.entityToDto(ingredient));
        }

        return ingredientResponseDTOList;
    }

    public IngredientResponseDTO getIngredientById(Long id, String jwt) throws IngredientException {
        String restName = jwtService.extractRestName(jwt);
        Ingredient ingredient = ingredientRepository.findById(id, restName).orElseThrow(() -> new IngredientException("No ingredient found with this id", 404));
        return ingredientMapper.entityToDto(ingredient);
    }

    public IngredientResponseDTO insertIngredient(IngredientRequestDTO ingredientRequestDTO, String jwt) throws IngredientException {
        String restName = jwtService.extractRestName(jwt);
        try {
            ingredientValidator.isRawMaterialPerDishValid(ingredientRequestDTO);
            Ingredient ingredient = ingredientMapper.dtoToEntity(ingredientRequestDTO);
            ingredient.setRestName(restName);
            ingredientRepository.saveAndFlush(ingredient);
            return ingredientMapper.entityToDto(ingredient);

        } catch (IngredientException e) {
            throw new IngredientException(e.getMessage(), e.getStatusCode());
        }
    }

    public IngredientResponseDTO updateIngredientById(Long id, IngredientRequestDTO ingredientRequestDTO, String jwt) throws IngredientException {
        String restName = jwtService.extractRestName(jwt);

        try {
            ingredientValidator.isRawMaterialPerDishValid(ingredientRequestDTO);
            Ingredient ingredient = ingredientRepository.findById(id, restName).orElseThrow(() -> new IngredientException("No ingredient found with this id", 404));
            ingredient.setQuantity(ingredientRequestDTO.getQuantity());
            ingredient.setRawMaterial(ingredientRequestDTO.getRawMaterial());

            Ingredient saved = ingredientRepository.saveAndFlush(ingredient);
            return ingredientMapper.entityToDto(saved);
        } catch (IngredientException e) {
            throw new IngredientException(e.getMessage(), e.getStatusCode());
        }
    }

    public IngredientResponseDTO deleteIngredientById(Long id, String jwt) throws IngredientException {
        String restName = jwtService.extractRestName(jwt);
        Ingredient ingredient = ingredientRepository.findById(id, restName).orElseThrow(() -> new IngredientException("Ingredient not found with this id", 404));
        ingredientRepository.deleteById(id);
        return ingredientMapper.entityToDto(ingredient);
    }
}
