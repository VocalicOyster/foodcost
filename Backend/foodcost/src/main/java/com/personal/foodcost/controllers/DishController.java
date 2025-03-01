package com.personal.foodcost.controllers;

import com.personal.foodcost.exceptions.DishException;
import com.personal.foodcost.exceptions.IngredientException;
import com.personal.foodcost.filter.JwtAuthFilter;
import com.personal.foodcost.models.DTOs.Response;
import com.personal.foodcost.models.DTOs.ResponseInvalid;
import com.personal.foodcost.models.DTOs.ResponseValid;
import com.personal.foodcost.models.DTOs.request_dto.DishRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.DishResponseDTO;
import com.personal.foodcost.models.DTOs.ResponseValidNoData;
import com.personal.foodcost.services.DishServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishServices dishServices;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @PostMapping //ADMIN e OWNER
    public ResponseEntity<Response> insertDish(@RequestBody DishRequestDTO dishRequestDTO, HttpServletRequest request) {

        String jwt = jwtAuthFilter.parseJwt(request);

        try {
            DishResponseDTO dishResponseDTO = dishServices.insertDish(dishRequestDTO, jwt);

            return ResponseEntity.ok().body(
                    new ResponseValidNoData(
                            200,
                            "Dish Inserted successfully!"
                    )
            );
        }
        catch (DishException | IngredientException e) {
            return ResponseEntity.status(400).body(
                    new ResponseInvalid(
                            400,
                            e.getMessage()
                    )
            );
        }
    }

    @GetMapping //TUTTI
    public ResponseEntity<Response> getAllDishes(HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);

        List<DishResponseDTO> dishResponseDTOList = dishServices.getAllDishes(jwt);
        if(dishResponseDTOList.isEmpty()) {
            return ResponseEntity.ok().body(
                    new ResponseValidNoData(
                            200,
                            "No dishes in database"
                    )
            );
        }

        return ResponseEntity.ok().body(
                new ResponseValid(
                        200,
                        "Dishes retrieved!",
                        dishResponseDTOList
                )
        );
    }

    @GetMapping("/{id}") //TUTTI
    public ResponseEntity<Response> getDishById(@PathVariable Long id, HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);
        try {
            DishResponseDTO dishResponseDTO = dishServices.getDishById(id, jwt);

            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Dish retrieved!",
                            dishResponseDTO
                    )
            );
        } catch (DishException e) {
            return ResponseEntity.status(404).body(
                    new ResponseInvalid(
                            404,
                            "No dishes retrieved with this id"
                    )
            );
        }
    }

    @PutMapping("/{id}") //ADMIN E OWNER
    public ResponseEntity<Response> updateDishById(@PathVariable Long id, @RequestBody DishRequestDTO dishRequestDTO, HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);
        try {
            DishResponseDTO dishResponseDTO = dishServices.updateDishById(id, dishRequestDTO, jwt);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Dish updated successfully",
                            dishResponseDTO
                    )
            );
        } catch (DishException e) {

            return ResponseEntity.status(404).body(
                    new ResponseInvalid(
                            404,
                            e.getMessage()
                    )
            );
        }
    }

    @DeleteMapping("/{id}") //ADMIN e OWNER
    public ResponseEntity<Response> deleteDishById(@PathVariable Long id, HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);

        try {
            DishResponseDTO dishResponseDTO = dishServices.deleteDishById(id, jwt);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Dish deleted",
                            dishResponseDTO
                    )
            );
        }
        catch (DishException e) {
            return ResponseEntity.status(404).body(
                    new ResponseInvalid(
                            404,
                            e.getMessage()
                    )
            );
        }
    }
}
