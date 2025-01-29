package com.personal.foodcost.validators;

import com.personal.foodcost.entities.User;
import com.personal.foodcost.exceptions.UserException;
import com.personal.foodcost.models.DTOs.Request.UserRequestDTO;
import com.personal.foodcost.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    public boolean isUserValid(UserRequestDTO userRequestDTO) {

    }

    private boolean isUserNotNull(UserRequestDTO userRequestDTO) throws UserException {
        if (!Objects.equals(userRequestDTO.getCellphone(), "") &&
                userRequestDTO.getRole() != null &&
                !Objects.equals(userRequestDTO.getName(), "") &&
                !Objects.equals(userRequestDTO.getUsername(), "") &&
                !Objects.equals(userRequestDTO.getPassword(), "") &&
                !Objects.equals(userRequestDTO.getRepeatPass(), "") &&
                userRequestDTO.getRestaurant() != null) {
            return true;
        } else throw new UserException("Something missing...", 400);
    }

    private boolean isUsernameNotInDatabase(UserRequestDTO userRequestDTO) throws UserException {
        User user = userRepository.findByUsername(userRequestDTO.getUsername()).orElse(null);

        if (user == null) return true;
        else throw new UserException("User with this username already in database", 400);
    }

    private boolean isPasswordValid(UserRequestDTO userRequestDTO) {
        Pattern pattern = Pattern.compile("^( ?=.*[a-z]{7,}) (?=.*[A-Z]) (?=.*[!@.$%^#?&*]) (?=.*\\d).{10,}$");
        Matcher matcher = pattern.matcher(userRequestDTO.getPassword());
    }
}
