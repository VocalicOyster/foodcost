package com.personal.foodcost.services;

import com.personal.foodcost.entities.Dish;
import com.personal.foodcost.exceptions.DishException;
import com.personal.foodcost.mappers.DishMapper;
import com.personal.foodcost.models.DTOs.request_dto.DishRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.DishResponseDTO;
import com.personal.foodcost.repositories.DishRepository;
import com.personal.foodcost.repositories.RawMaterialPerDishRepository;
import com.personal.foodcost.validators.DishValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishServices {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishValidators dishValidators;

    @Autowired
    private RawMaterialPerDishRepository rawMaterialPerDishRepository;

    public DishResponseDTO insertDish(DishRequestDTO dishRequestDTO) throws DishException {
        try {
            dishValidators.isDishValid(dishRequestDTO);

            Dish dish = dishMapper.dtoToEntity(dishRequestDTO);
            Dish savedDish = dishRepository.saveAndFlush(dish);
            return dishMapper.entityToDto(savedDish);

        } catch (DishException e) {
            throw new DishException(e.getMessage(), 400);
        }
    }
}
