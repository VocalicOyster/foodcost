package com.personal.foodcost.mappers;

import com.personal.foodcost.entities.Dish;
import com.personal.foodcost.models.DTOs.request_dto.DishRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.DishResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

    //mapper to substitute ModelMapper

    public Dish dtoToEntity(DishRequestDTO dishRequestDTO) {
        Dish dish = new Dish();

        dish.setId(dishRequestDTO.getId());
        dish.setName(dishRequestDTO.getName());
        dish.setPrice(dishRequestDTO.getPrice());
        dish.setRawMaterialList(dishRequestDTO.getRowMaterialList());
        dish.setDescription(dishRequestDTO.getDescription());

        return dish;
    }

    public DishResponseDTO entityToDto(Dish dish) {
        DishResponseDTO dishResponseDTO = new DishResponseDTO();

        dishResponseDTO.setId(dish.getId());
        dishResponseDTO.setPrice(dish.getPrice());
        dishResponseDTO.setName(dish.getName());
        dishResponseDTO.setDescription(dish.getDescription());

        return dishResponseDTO;
    }
}
