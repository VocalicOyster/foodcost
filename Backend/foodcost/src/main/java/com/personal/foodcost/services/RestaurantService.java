package com.personal.foodcost.services;

import com.personal.foodcost.entities.Restaurant;
import com.personal.foodcost.exceptions.RestaurantException;
import com.personal.foodcost.mappers.RestaurantMapper;
import com.personal.foodcost.models.DTOs.request_dto.RestaurantRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.RestaurantResponseDTO;
import com.personal.foodcost.repositories.RestaurantRepository;
import com.personal.foodcost.validators.RestaurantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantValidator restaurantValidator;

    public List<RestaurantResponseDTO> getAll() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        List<RestaurantResponseDTO> restaurantResponseDTOList = new ArrayList<>();
        for(Restaurant restaurant: restaurantList) {
            restaurantResponseDTOList.add(restaurantMapper.entityToDto(restaurant));
        }
        return restaurantResponseDTOList;
    }

    public RestaurantResponseDTO getById(Long id) throws RestaurantException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantException("No restaurant retrieved with this id", 404));

        return restaurantMapper.entityToDto(restaurant);
    }

    public RestaurantResponseDTO insertRestaurant(RestaurantRequestDTO restaurantRequestDTO) throws RestaurantException {
        try {
            restaurantValidator.isRestaurantValid(restaurantRequestDTO);
            Restaurant restaurant = restaurantMapper.dtoToEntity(restaurantRequestDTO);
            Restaurant saved = restaurantRepository.saveAndFlush(restaurant);
            return restaurantMapper.entityToDto(saved);
        } catch (RestaurantException e) {
            throw new RestaurantException(e.getMessage(), e.getStatusCode());
        }
    }

    public RestaurantResponseDTO updateRestaurant(Long id, RestaurantRequestDTO restaurantRequestDTO) throws RestaurantException {
        try {
            restaurantValidator.isRestaurantValid(restaurantRequestDTO);
            Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantException("No restaurant retrieved with this id", 404));

            restaurant.setpIva(restaurantRequestDTO.getpIva());
            restaurant.setCellphone(restaurantRequestDTO.getCellphone());
            restaurant.setName(restaurantRequestDTO.getName());
            restaurant.setEmail(restaurantRequestDTO.getEmail());
            restaurant.setAddress(restaurantRequestDTO.getAddress());
            restaurant.setTelephoneNumber(restaurantRequestDTO.getTelephoneNumber());

            Restaurant saved = restaurantRepository.saveAndFlush(restaurant);

            return restaurantMapper.entityToDto(saved);

        } catch (RestaurantException e) {
            throw new RestaurantException(e.getMessage(), e.getStatusCode());
        }
    }

    public RestaurantResponseDTO deleteById(Long id) throws RestaurantException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantException("No restaurant retrieved with this id", 404));

        restaurantRepository.deleteById(id);

        return restaurantMapper.entityToDto(restaurant);
    }
}
