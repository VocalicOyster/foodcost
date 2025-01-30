package com.personal.foodcost.mappers;

import com.personal.foodcost.entities.Restaurant;
import com.personal.foodcost.models.DTOs.request_dto.RestaurantRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.RestaurantResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public Restaurant dtoToEntity(RestaurantRequestDTO restaurantRequestDTO) {
        Restaurant restaurant = new Restaurant();

        restaurant.setId(restaurantRequestDTO.getId());
        restaurant.setpIva(restaurantRequestDTO.getpIva());
        restaurant.setAddress(restaurantRequestDTO.getAddress());
        restaurant.setName(restaurantRequestDTO.getName());
        restaurant.setEmail(restaurantRequestDTO.getEmail());
        restaurant.setCellphone(restaurantRequestDTO.getCellphone());
        restaurant.setTelephoneNumber(restaurantRequestDTO.getTelephoneNumber());

        return restaurant;
    }

    public RestaurantResponseDTO entityToDto(Restaurant restaurant) {
        RestaurantResponseDTO restaurantResponseDTO = new RestaurantResponseDTO();

        restaurantResponseDTO.setId(restaurant.getId());
        restaurantResponseDTO.setpIva(restaurant.getpIva());
        restaurantResponseDTO.setName(restaurant.getName());
        restaurantResponseDTO.setAddress(restaurant.getAddress());

        return restaurantResponseDTO;
    }
}
