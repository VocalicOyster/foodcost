package com.personal.foodcost.validators;

import com.personal.foodcost.entities.Dish;
import com.personal.foodcost.models.DTOs.Request.DishRequestDTO;
import com.personal.foodcost.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DishValidators {

    @Autowired
    private DishRepository dishRepository;

    public boolean isDishValid(DishRequestDTO dishRequestDTO) {
        return isDishNotNull(dishRequestDTO) &&
                isPriceValid(dishRequestDTO) &&
                isNotInDatabase(dishRequestDTO);
    }

    private boolean isDishNotNull(DishRequestDTO dishRequestDTO) {
        return !Objects.equals(dishRequestDTO.getName(), "") &&
                dishRequestDTO.getPrice() != null &&
                !dishRequestDTO.getRowMaterialList().isEmpty();
    }

    private boolean isPriceValid(DishRequestDTO dishRequestDTO) {
        return !dishRequestDTO.getPrice().isNaN();
    }

    private boolean isNotInDatabase(DishRequestDTO dishRequestDTO) {
        Dish dish = dishRepository.findByName(dishRequestDTO.getName()).orElse(null);
        return dish == null;
    }
}
