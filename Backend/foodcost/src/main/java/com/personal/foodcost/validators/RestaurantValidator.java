package com.personal.foodcost.validators;

import com.personal.foodcost.entities.Restaurant;
import com.personal.foodcost.exceptions.RestaurantException;
import com.personal.foodcost.models.DTOs.request_dto.RestaurantRequestDTO;
import com.personal.foodcost.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RestaurantValidator {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public boolean isRestaurantValid(RestaurantRequestDTO restaurantRequestDTO) throws RestaurantException {
        if (!Objects.equals(restaurantRequestDTO.getCellphone(), "")) {
            if (!isCellphoneNumberValid(restaurantRequestDTO)) {
                throw new RestaurantException("Cellphone number is not valid", 400);
            }
        }

        if (!Objects.equals(restaurantRequestDTO.getTelephoneNumber(), "")) {
            if (!isTelephoneNumberValid(restaurantRequestDTO)) {
                throw new RestaurantException("Telephone number is not valid", 400);
            }
        }

        try {
            return isRestaurantNotNull(restaurantRequestDTO) &&
                    isRestaurantNotInDatabase(restaurantRequestDTO) &&
                    isEmailValid(restaurantRequestDTO) &&
                    isPIvaValid(restaurantRequestDTO);
        } catch (RestaurantException e) {
            throw new RestaurantException(e.getMessage(), 400);
        }

    }

    private boolean isRestaurantNotNull(RestaurantRequestDTO restaurantRequestDTO) throws RestaurantException {
        if (!Objects.equals(restaurantRequestDTO.getEmail(), "") &&
                !Objects.equals(restaurantRequestDTO.getpIva(), "") &&
                !Objects.equals(restaurantRequestDTO.getAddress(), "") &&
                !Objects.equals(restaurantRequestDTO.getName(), "")) {
            return true;
        } else throw new RestaurantException("Something missing...", 400);

        //telephone and cellphone numbers are not required
    }

    private boolean isRestaurantNotInDatabase(RestaurantRequestDTO restaurantRequestDTO) throws RestaurantException {
        Restaurant restaurant = restaurantRepository.findByPIva(restaurantRequestDTO.getpIva()).orElse(null);
        if (restaurant == null) {
            return true;
        } else throw new RestaurantException("Restaurant in already in database", 400);
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

    private boolean isEmailValid(RestaurantRequestDTO restaurantRequestDTO) throws RestaurantException {
        Pattern pattern = Pattern.compile("[a-zA-Z.0-9]+@+[a-zA-Z.].[a-z]{2,6}");
        Matcher matcher = pattern.matcher(restaurantRequestDTO.getEmail());
        if (matcher.matches()) {
            return true;
        } else throw new RestaurantException("Email is not valid", 400);

    }

    private boolean isPIvaValid(RestaurantRequestDTO restaurantRequestDTO) throws RestaurantException {
        Pattern pattern = Pattern.compile("[0-9]{11}");
        Matcher matcher = pattern.matcher(restaurantRequestDTO.getpIva());
        if (matcher.matches()) {
            return true;
        } else throw new RestaurantException("P.Iva number is not valid", 400);
    }

}
