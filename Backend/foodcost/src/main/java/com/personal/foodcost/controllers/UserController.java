package com.personal.foodcost.controllers;

import com.personal.foodcost.exceptions.UserException;
import com.personal.foodcost.models.DTOs.*;
import com.personal.foodcost.models.DTOs.request_dto.UserRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.UserResponseDTO;
import com.personal.foodcost.services.JwtService;
import com.personal.foodcost.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<Response> getAll() {
        List<UserResponseDTO> userResponseDTOList = userService.getAll();

        if(userResponseDTOList.isEmpty()) {
            return ResponseEntity.ok().body(
                    new ResponseValidNoData(
                            204,
                            "No user retrieved!"
                    )
            );
        }

        return ResponseEntity.ok().body(
                new ResponseValid(
                        200,
                        "User retrieved!",
                        userResponseDTOList
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {

        try {
            UserResponseDTO userResponseDTO = userService.getById(id);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "User retrieved!",
                            userResponseDTO
                    )
            );
        } catch (UserException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseValidNoData(
                            e.getStatusCode(),
                            "No user retrieved with this id!"
                    )
            );
        }
    }

    @PostMapping
    public ResponseEntity<Response> insertUser(@RequestBody UserRequestDTO userRequestDTO) {

        try {
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

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO userResponseDTO = userService.updateById(id, userRequestDTO);

            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "User updated!",
                            userResponseDTO
                    )
            );
        } catch (UserException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable Long id) {
        try {
            UserResponseDTO userResponseDTO = userService.deleteById(id);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "User deleted!",
                            userResponseDTO
                    )
            );
        } catch (UserException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

}
