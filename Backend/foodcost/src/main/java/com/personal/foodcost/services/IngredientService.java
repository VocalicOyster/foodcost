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

    public List<IngredientResponseDTO> getAllIngredients() {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        List<IngredientResponseDTO> ingredientResponseDTOList = new ArrayList<>();

        for(Ingredient ingredient: ingredientList) {
            ingredientResponseDTOList.add(ingredientMapper.entityToDto(ingredient));
        }

        return ingredientResponseDTOList;
    }

    public IngredientResponseDTO getIngredientById(Long id) throws IngredientException {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new IngredientException("No ingredient found with this id", 404));
        return ingredientMapper.entityToDto(ingredient);
    }

    public IngredientResponseDTO insertIngredient(IngredientRequestDTO ingredientRequestDTO) throws IngredientException {
        try {
            ingredientValidator.isRawMaterialPerDishValid(ingredientRequestDTO);
            Ingredient ingredient = ingredientMapper.dtoToEntity(ingredientRequestDTO);
            ingredientRepository.saveAndFlush(ingredient);
            return ingredientMapper.entityToDto(ingredient);

        } catch (IngredientException e) {
            throw new IngredientException(e.getMessage(), e.getStatusCode());
        }
    }

    public IngredientResponseDTO updateIngredientById(Long id, IngredientRequestDTO ingredientRequestDTO) throws IngredientException {
        try {
            ingredientValidator.isRawMaterialPerDishValid(ingredientRequestDTO);
            Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new IngredientException("No ingredient found with this id", 404));
            ingredient.setQuantity(ingredientRequestDTO.getQuantity());
            ingredient.setRawMaterial(ingredientRequestDTO.getRawMaterial());

            Ingredient saved = ingredientRepository.saveAndFlush(ingredient);
            return ingredientMapper.entityToDto(saved);
        } catch (IngredientException e) {
            throw new IngredientException(e.getMessage(), e.getStatusCode());
        }
    }

    public IngredientResponseDTO deleteIngredientById(Long id) throws IngredientException {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new IngredientException("Ingredient not found with this id", 404));
        ingredientRepository.deleteById(id);
        return ingredientMapper.entityToDto(ingredient);
    }
}
