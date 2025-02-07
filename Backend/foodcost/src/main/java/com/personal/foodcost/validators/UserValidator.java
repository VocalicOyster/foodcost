package com.personal.foodcost.validators;

import com.personal.foodcost.entities.MyUser;
import com.personal.foodcost.exceptions.UserException;
import com.personal.foodcost.models.DTOs.request_dto.UserRequestDTO;
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

    public boolean isUserValid(UserRequestDTO userRequestDTO) throws UserException {
        try {
          return isUserNotNull(userRequestDTO) &&
                 isPasswordValid(userRequestDTO) &&
                 isUsernameNotInDatabase(userRequestDTO);
        } catch (UserException e) {
            throw new UserException(e.getMessage(), 400);
        }
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
        MyUser myUser = userRepository.findByUsername(userRequestDTO.getUsername()).orElse(null);

        if (myUser == null) return true;
        else throw new UserException("User with this username already in database", 400);
    }

    private boolean isPasswordValid(UserRequestDTO userRequestDTO) throws UserException {
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[!@.$%^#?&*])(?=.*\\d).{10,}$");
        Matcher matcher = pattern.matcher(userRequestDTO.getPassword());
        if(matcher.matches()) {
            return true;
        } else throw new UserException("Password is not valid", 400);
    }

    private boolean equalsPasswords(UserRequestDTO userRequestDTO) throws UserException {
        if(userRequestDTO.getPassword().equals(userRequestDTO.getRepeatPass())) {
            return true;
        } else throw new UserException("Passwords do not match", 400);
    }
}
