package com.personal.foodcost.controllers;

import com.personal.foodcost.exceptions.UserException;
import com.personal.foodcost.models.DTOs.*;
import com.personal.foodcost.models.DTOs.request_dto.UserRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.UserResponseDTO;
import com.personal.foodcost.services.JwtService;
import com.personal.foodcost.services.UserService;
import com.personal.foodcost.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Controller("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/login")
    public ResponseEntity<Response> authAndGetToken(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Authenticated!",
                            "Token: " + jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()))
                    )
            );
        }
        return ResponseEntity.status(404).body(
                new ResponseInvalid(
                        404,
                        "No user found with this username"
                )
        );
    }

    @PostMapping("/ownerRes")
    public ResponseEntity<Response> registrationNewRestaurant(@RequestBody UserRequestDTO userRequestDTO) {
        try {

            userValidator.isUserValid(userRequestDTO);

            if(!Objects.equals(userRequestDTO.getRole().toString(), ROLES.OWNER.name())) {
                return ResponseEntity.status(400).body(
                        new ResponseInvalid(
                                400,
                                "You're trying to register anything different from restaurant. INVALID ENDPOINT!"
                        )
                );
            }

            UserResponseDTO userResponseDTO = userService.insertUser(userRequestDTO);

            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "User inserted!",
                            userResponseDTO
                    )
            );
        } catch (UserException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseValidNoData(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

    @PostMapping("employeeRes")
    public ResponseEntity<Response> registrationNewEmployee(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            userValidator.isUserValid(userRequestDTO);
            if(!Objects.equals(userRequestDTO.getRole().toString(), ROLES.EMPLOYEE.name())) {
                return ResponseEntity.status(400).body(
                        new ResponseInvalid(
                                400,
                                "You're trying to register anything different from employee. INVALID ENDPOINT!"
                        )
                );
            }

            UserResponseDTO userResponseDTO = userService.insertUser(userRequestDTO);

            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "User inserted!",
                            userResponseDTO
                    )
            );
        } catch (UserException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseValidNoData(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }
}
