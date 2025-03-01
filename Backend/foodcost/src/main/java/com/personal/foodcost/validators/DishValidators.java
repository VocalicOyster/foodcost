package com.personal.foodcost.validators;

import com.personal.foodcost.entities.Dish;
import com.personal.foodcost.exceptions.DishException;
import com.personal.foodcost.filter.JwtAuthFilter;
import com.personal.foodcost.models.DTOs.request_dto.DishRequestDTO;
import com.personal.foodcost.repositories.DishRepository;
import com.personal.foodcost.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DishValidators {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private JwtService jwtService;

    public boolean isDishValid(DishRequestDTO dishRequestDTO, String jwt) throws DishException {
        try {
            return isDishNotNull(dishRequestDTO) &&
                    isPriceValid(dishRequestDTO) &&
                    isNotInDatabase(dishRequestDTO, jwt);
        }
        catch (DishException e) {
            throw new DishException(e.getMessage(), 400);
        }
    }

    private boolean isDishNotNull(DishRequestDTO dishRequestDTO) throws DishException {
        if(!Objects.equals(dishRequestDTO.getName(), "") &&
                dishRequestDTO.getPrice() != null &&
                dishRequestDTO.getIngredientList() != null) {
            return true;
        } else throw new DishException("Something missing...", 400);
    }

    private boolean isPriceValid(DishRequestDTO dishRequestDTO) throws DishException {
        if(!dishRequestDTO.getPrice().isNaN()) {
            return true;
        } else throw new DishException("Price is not valid", 400);
    }

    private boolean isNotInDatabase(DishRequestDTO dishRequestDTO, String jwt) throws DishException {
        String restName = jwtService.extractRestName(jwt);
        Dish dish = dishRepository.findByName(dishRequestDTO.getName(), restName).orElse(null);
        if(dish == null) return true;
        else throw new DishException("Dish is already in database", 400);
    }
}
