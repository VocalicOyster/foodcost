package com.personal.foodcost.services;

import com.personal.foodcost.entities.Dish;
import com.personal.foodcost.entities.Ingredient;
import com.personal.foodcost.exceptions.DishException;
import com.personal.foodcost.exceptions.IngredientException;
import com.personal.foodcost.mappers.DishMapper;
import com.personal.foodcost.mappers.IngredientMapper;
import com.personal.foodcost.models.DTOs.request_dto.DishRequestDTO;
import com.personal.foodcost.models.DTOs.request_dto.IngredientRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.DishResponseDTO;
import com.personal.foodcost.models.DTOs.response_dto.IngredientResponseDTO;
import com.personal.foodcost.repositories.DishRepository;
import com.personal.foodcost.repositories.IngredientRepository;
import com.personal.foodcost.validators.DishValidators;
import com.personal.foodcost.validators.IngredientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServices {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private DishValidators dishValidators;

    @Autowired
    private IngredientValidator ingredientValidator;

    @Autowired
    private IngredientRepository ingredientRepository;

    public DishResponseDTO insertDish(DishRequestDTO dishRequestDTO) throws DishException, IngredientException {
        try {
            for(IngredientRequestDTO ingredientRequestDTO : dishRequestDTO.getIngredientList()) {
                ingredientValidator.isRawMaterialPerDishValid(ingredientRequestDTO);
            }

            List<Ingredient> list = new ArrayList<>();

            for(IngredientRequestDTO ingredientRequestDTO : dishRequestDTO.getIngredientList()) {
                Ingredient ingredient = ingredientMapper.dtoToEntity(ingredientRequestDTO);
                list.add(ingredientRepository.saveAndFlush(ingredient));
            }

            dishValidators.isDishValid(dishRequestDTO);

            Dish dish = dishMapper.dtoToEntity(dishRequestDTO);
            dish.setIngredientList(list);
            Dish savedDish = dishRepository.saveAndFlush(dish);
            return dishMapper.entityToDto(savedDish);

        } catch (DishException e) {
            throw new DishException(e.getMessage(), 400);
        } catch (IngredientException e) {
            throw new IngredientException(e.getMessage(), 400);
        }
    }

    public List<DishResponseDTO> getAllDishes() {
        List<Dish> dishList = dishRepository.findAll();
        IngredientResponseDTO ingredientResponseDTO = new IngredientResponseDTO();

        List<DishResponseDTO> dishResponseDTOList = new ArrayList<>();
        List<Ingredient> ingredientList = new ArrayList<>();

        for (Dish dish: dishList) {
            DishResponseDTO dishResponseDTO = dishMapper.entityToDto(dish);
            dishResponseDTOList.add(dishResponseDTO);
        }

        return dishResponseDTOList;
    }

    public DishResponseDTO getDishById(Long id) throws DishException {
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new DishException("No dishes find with this id", 400));
        return dishMapper.entityToDto(dish);
    }

    public DishResponseDTO updateDishById(Long id, DishRequestDTO dishRequestDTO) throws DishException {
        Dish dish = dishRepository.findById(id).orElse(null);

        if(dish == null) throw new DishException("No dishes retrieved with this id", 404);

        dish.setName(dishRequestDTO.getName());
        dish.setPrice(dishRequestDTO.getPrice());
        dish.setDescription(dishRequestDTO.getDescription());

        List<Ingredient> list = new ArrayList<>();

        for(IngredientRequestDTO ingredientDTO: dishRequestDTO.getIngredientList()) {
            list.add(ingredientMapper.dtoToEntity(ingredientDTO));
        }

        for(Ingredient ingredient: list) {
            ingredientRepository.saveAndFlush(ingredient);
        }

        dish.setIngredientList(list);
        Dish saved = dishRepository.saveAndFlush(dish);

        return dishMapper.entityToDto(saved);
    }

    public DishResponseDTO deleteDishById(Long id) throws DishException {
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new DishException("Dish not found with this id", 404));
        dishRepository.deleteById(id);

        return dishMapper.entityToDto(dish);
    }
}
