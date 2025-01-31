package com.personal.foodcost.mappers;

import com.personal.foodcost.entities.Dish;
import com.personal.foodcost.entities.Ingredient;
import com.personal.foodcost.models.DTOs.request_dto.DishRequestDTO;
import com.personal.foodcost.models.DTOs.request_dto.IngredientRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.DishResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DishMapper {

    //mapper to substitute ModelMapper

    @Autowired
    private IngredientMapper ingredientMapper;

    public Dish dtoToEntity(DishRequestDTO dishRequestDTO) {
        Dish dish = new Dish();

        dish.setId(dishRequestDTO.getId());
        List<Ingredient> list = new ArrayList<>();
        for(IngredientRequestDTO ingredient: dishRequestDTO.getIngredientList()) {
            list.add(ingredientMapper.dtoToEntity(ingredient));
        }
        dish.setIngredientList(list);
        dish.setDescription(dishRequestDTO.getDescription());
        dish.setName(dishRequestDTO.getName());
        dish.setPrice(dishRequestDTO.getPrice());

        return dish;
    }

    public DishResponseDTO entityToDto(Dish dish) {
        DishResponseDTO dishResponseDTO = new DishResponseDTO();

        dishResponseDTO.setId(dish.getId());
        dishResponseDTO.setName(dish.getName());
        dishResponseDTO.setDescription(dish.getDescription());
        dishResponseDTO.setPrice(dish.getPrice());

        return dishResponseDTO;
    }
}
