package com.personal.foodcost.controllers;

import com.personal.foodcost.exceptions.DishException;
import com.personal.foodcost.models.DTOs.Response;
import com.personal.foodcost.models.DTOs.ResponseInvalid;
import com.personal.foodcost.models.DTOs.ResponseValid;
import com.personal.foodcost.models.DTOs.request_dto.DishRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.DishResponseDTO;
import com.personal.foodcost.services.DishServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishServices dishServices;

    @PostMapping
    public ResponseEntity<Response> insertDish(@RequestBody DishRequestDTO dishRequestDTO) {
        try {
            DishResponseDTO dishResponseDTO = dishServices.insertDish(dishRequestDTO);

            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Dish Inserted successfully!"
                    )
            );
        }
        catch (DishException e) {

            return ResponseEntity.status(400).body(
                    new ResponseInvalid(
                            400,
                            e.getMessage()
                    )
            );        }
    }
}
