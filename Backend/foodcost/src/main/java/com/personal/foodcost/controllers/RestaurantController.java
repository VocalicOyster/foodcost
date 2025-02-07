package com.personal.foodcost.controllers;

import com.personal.foodcost.entities.Restaurant;
import com.personal.foodcost.exceptions.RestaurantException;
import com.personal.foodcost.models.DTOs.Response;
import com.personal.foodcost.models.DTOs.ResponseInvalid;
import com.personal.foodcost.models.DTOs.ResponseValid;
import com.personal.foodcost.models.DTOs.ResponseValidNoData;
import com.personal.foodcost.models.DTOs.request_dto.RestaurantRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.RestaurantResponseDTO;
import com.personal.foodcost.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping //SOLO ADMIN
    public ResponseEntity<Response> getAll() {
        List<RestaurantResponseDTO> restaurantResponseDTOList = restaurantService.getAll();
        if (restaurantResponseDTOList.isEmpty()) {
            return ResponseEntity.status(200).body(
                    new ResponseValidNoData(
                            204,
                            "No restaurant retrieved!"
                    )
            );
        }

        return ResponseEntity.ok().body(
                new ResponseValid(
                        200,
                        "Restaurants retrieved!",
                        restaurantResponseDTOList
                )
        );
    }


    @GetMapping("{id}") //TUTTI
    public ResponseEntity<Response> getById(@PathVariable Long id) {

        try {
            RestaurantResponseDTO restaurantResponseDTO = restaurantService.getById(id);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Restaurant retrieved!",
                            restaurantResponseDTO
                    )
            );
        } catch (RestaurantException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

    @PostMapping //SOLO ADMIN
    public ResponseEntity<Response> insertRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        try {
            RestaurantResponseDTO restaurantResponseDTO = restaurantService.insertRestaurant(restaurantRequestDTO);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Restaurant inserted!",
                            restaurantResponseDTO
                    )
            );
        } catch (RestaurantException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

    @PutMapping("/{id}") //ADMIN e OWNER
    public ResponseEntity<Response> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        try {
            RestaurantResponseDTO restaurantResponseDTO = restaurantService.updateRestaurant(id, restaurantRequestDTO);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Restaurant updated!",
                            restaurantResponseDTO
                    )
            );
        } catch (RestaurantException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

    @DeleteMapping("/{id}") //SOLO ADMIN
    public ResponseEntity<Response> deleteById(@PathVariable Long id) {
        try {
            RestaurantResponseDTO restaurantResponseDTO = restaurantService.deleteById(id);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Restaurant deleted!",
                            restaurantResponseDTO
                    )
            );
        } catch (RestaurantException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }
}
