package com.personal.foodcost.validators;

import com.personal.foodcost.entities.Restaurant;
import com.personal.foodcost.models.DTOs.Request.RestaurantRequestDTO;
import com.personal.foodcost.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RestaurantValidator {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public boolean isRestaurantValid(RestaurantRequestDTO restaurantRequestDTO) {
        return isRestaurantNotNull(restaurantRequestDTO) &&
                isRestaurantNotInDatabase(restaurantRequestDTO) &&
                isTelephoneNumberValid(restaurantRequestDTO) &&
                isCellphoneNumberValid(restaurantRequestDTO) &&
                isEmailValid(restaurantRequestDTO) &&
                isPIvaValid(restaurantRequestDTO);

    }

    private boolean isRestaurantNotNull(RestaurantRequestDTO restaurantRequestDTO) {
        return restaurantRequestDTO.getEmail() != null &&
                restaurantRequestDTO.getpIva() != null &&
                restaurantRequestDTO.getAddress() != null &&
                restaurantRequestDTO.getName() != null;
        //telephone and cellphone numbers are not required
    }

    private boolean isRestaurantNotInDatabase(RestaurantRequestDTO restaurantRequestDTO) {
        Restaurant restaurant = restaurantRepository.findByPIva(restaurantRequestDTO.getpIva()).orElse(null);
        return restaurant == null;
    }

    private boolean isTelephoneNumberValid(RestaurantRequestDTO restaurantRequestDTO) {
        Pattern pattern = Pattern.compile("[0-9]{6,11}");
        Matcher matcher = pattern.matcher(restaurantRequestDTO.getTelephoneNumber());
        return matcher.matches();
    }

    private boolean isCellphoneNumberValid(RestaurantRequestDTO restaurantRequestDTO) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(restaurantRequestDTO.getCellphone());
        return matcher.matches();
    }

    public boolean isEmailValid(RestaurantRequestDTO restaurantRequestDTO) {
        Pattern pattern = Pattern.compile("[a-zA-Z.0-9]+@+[a-zA-Z.].[a-z]{2,6}");
        Matcher matcher = pattern.matcher(restaurantRequestDTO.getEmail());
        return matcher.matches();

    }

    private boolean isPIvaValid(RestaurantRequestDTO restaurantRequestDTO) {
        Pattern pattern = Pattern.compile("[0-9]{11}");
        Matcher matcher = pattern.matcher(restaurantRequestDTO.getpIva());
        return matcher.matches();
    }

}
