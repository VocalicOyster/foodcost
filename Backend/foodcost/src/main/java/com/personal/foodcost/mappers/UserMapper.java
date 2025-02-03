package com.personal.foodcost.mappers;

import com.personal.foodcost.entities.User;
import com.personal.foodcost.exceptions.RestaurantException;
import com.personal.foodcost.models.DTOs.request_dto.UserRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.UserResponseDTO;
import com.personal.foodcost.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public User dtoToEntity(UserRequestDTO userRequestDTO) throws RestaurantException {
        User user = new User();

        user.setId(userRequestDTO.getId());
        user.setName(userRequestDTO.getName());
        user.setRole(userRequestDTO.getRole());
        user.setPassword(userRequestDTO.getPassword());
        user.setRestaurant(restaurantRepository.findById(userRequestDTO.getRestaurant().getId()).orElseThrow(() -> new RestaurantException("No restaurant found", 400)));
        user.setUsername(userRequestDTO.getUsername());
        user.setCellphone(userRequestDTO.getCellphone());

        return user;
    }

    public UserResponseDTO entityToDto(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setRole(user.getRole());

        return userResponseDTO;

    }
}
