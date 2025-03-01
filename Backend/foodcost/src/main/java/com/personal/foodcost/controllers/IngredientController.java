package com.personal.foodcost.controllers;

import com.personal.foodcost.exceptions.IngredientException;
import com.personal.foodcost.filter.JwtAuthFilter;
import com.personal.foodcost.models.DTOs.Response;
import com.personal.foodcost.models.DTOs.ResponseInvalid;
import com.personal.foodcost.models.DTOs.ResponseValid;
import com.personal.foodcost.models.DTOs.request_dto.IngredientRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.IngredientResponseDTO;
import com.personal.foodcost.models.DTOs.ResponseValidNoData;
import com.personal.foodcost.services.IngredientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @GetMapping //TUTTI
    public ResponseEntity<Response> getAll(HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);
        List<IngredientResponseDTO> ingredientResponseDTOList = ingredientService.getAllIngredients(jwt);
        if(ingredientResponseDTOList.isEmpty()) {
            return ResponseEntity.status(204).body(
                    new ResponseValidNoData(
                            204,
                            "No ingredient retrieved!"
                    )
            );
        }

        return ResponseEntity.ok().body(
                new ResponseValid(
                        200,
                        "Ingredients retrieved!",
                        ingredientResponseDTOList
                )
        );
    }

    @GetMapping("/{id}") //TUTTI
    public ResponseEntity<Response> getIngredientById(@PathVariable Long id, HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);
        try {
            IngredientResponseDTO ingredientResponseDTO = ingredientService.getIngredientById(id, jwt);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Ingredient retrieved!",
                            ingredientResponseDTO
                    )
            );
        } catch (IngredientException e) {
            return ResponseEntity.status(404).body(
                    new ResponseInvalid(
                            404,
                            e.getMessage()
                    )
            );
        }
    }

    @PostMapping //ADMIN E OWNER
    public ResponseEntity<Response> insertIngredient(@RequestBody IngredientRequestDTO ingredientRequestDTO, HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);
        try {
            IngredientResponseDTO ingredientResponseDTO = ingredientService.insertIngredient(ingredientRequestDTO, jwt);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Ingredient inserted",
                            ingredientResponseDTO
                    )
            );
        } catch (IngredientException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

    @PutMapping("/{id}") //ADMIN E OWNER
    public ResponseEntity<Response> updateIngredientById(@PathVariable Long id, @RequestBody IngredientRequestDTO ingredientRequestDTO, HttpServletRequest request) {

        String jwt = jwtAuthFilter.parseJwt(request);

        try {
            IngredientResponseDTO ingredientResponseDTO = ingredientService.updateIngredientById(id, ingredientRequestDTO, jwt);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Ingredient updated!",
                            ingredientResponseDTO
                    )
            );
        } catch (IngredientException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

    @DeleteMapping("/{id}") //ADMIN E OWNER
    public ResponseEntity<Response> deleteIngredientById(@PathVariable Long id, HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);
        try {
            IngredientResponseDTO ingredientResponseDTO = ingredientService.deleteIngredientById(id, jwt);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Ingredient deleted!",
                            ingredientResponseDTO
                    )
            );
        } catch (IngredientException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }
}
