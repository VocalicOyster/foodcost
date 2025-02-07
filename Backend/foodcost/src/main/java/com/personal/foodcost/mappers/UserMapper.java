package com.personal.foodcost.mappers;

import com.personal.foodcost.entities.MyUser;
import com.personal.foodcost.exceptions.UserException;
import com.personal.foodcost.models.DTOs.request_dto.UserRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.UserResponseDTO;
import com.personal.foodcost.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public MyUser dtoToEntity(UserRequestDTO userRequestDTO) throws UserException {
        MyUser myUser = new MyUser();

        myUser.setId(userRequestDTO.getId());
        myUser.setName(userRequestDTO.getName());
        myUser.setRole(userRequestDTO.getRole());
        myUser.setPassword(userRequestDTO.getPassword());
        myUser.setRestaurant(restaurantRepository.findById(userRequestDTO.getRestaurant().getId()).orElseThrow(() -> new UserException("No restaurant found", 400)));
        myUser.setUsername(userRequestDTO.getUsername());
        myUser.setCellphone(userRequestDTO.getCellphone());

        return myUser;
    }

    public UserResponseDTO entityToDto(MyUser myUser) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(myUser.getId());
        userResponseDTO.setName(myUser.getName());
        userResponseDTO.setRole(myUser.getRole());

        return userResponseDTO;

    }
}
