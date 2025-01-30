package com.personal.foodcost.validators;

import com.personal.foodcost.entities.Dish;
import com.personal.foodcost.exceptions.DishException;
import com.personal.foodcost.models.DTOs.request_dto.DishRequestDTO;
import com.personal.foodcost.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DishValidators {

    @Autowired
    private DishRepository dishRepository;

    public boolean isDishValid(DishRequestDTO dishRequestDTO) throws DishException {
        try {
            return isDishNotNull(dishRequestDTO) &&
                    isPriceValid(dishRequestDTO) &&
                    isNotInDatabase(dishRequestDTO);
        }
        catch (DishException e) {
            throw new DishException(e.getMessage(), 400);
        }
    }

    private boolean isDishNotNull(DishRequestDTO dishRequestDTO) throws DishException {
        if(!Objects.equals(dishRequestDTO.getName(), "") &&
                dishRequestDTO.getPrice() != null &&
                !dishRequestDTO.getRowMaterialList().isEmpty()) {
            return true;
        } else throw new DishException("Something missing...", 400);
    }

    private boolean isPriceValid(DishRequestDTO dishRequestDTO) throws DishException {
        if(!dishRequestDTO.getPrice().isNaN()) {
            return true;
        } else throw new DishException("Price is not valid", 400);
    }

    private boolean isNotInDatabase(DishRequestDTO dishRequestDTO) throws DishException {
        Dish dish = dishRepository.findByName(dishRequestDTO.getName()).orElse(null);
        if(dish == null) return true;
        else throw new DishException("Dish is already in database", 400);
    }
}
