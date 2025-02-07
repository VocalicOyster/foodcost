package com.personal.foodcost.controllers;

import com.personal.foodcost.exceptions.IngredientException;
import com.personal.foodcost.models.DTOs.Response;
import com.personal.foodcost.models.DTOs.ResponseInvalid;
import com.personal.foodcost.models.DTOs.ResponseValid;
import com.personal.foodcost.models.DTOs.request_dto.IngredientRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.IngredientResponseDTO;
import com.personal.foodcost.models.DTOs.ResponseValidNoData;
import com.personal.foodcost.services.IngredientService;
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

    @GetMapping //TUTTI
    public ResponseEntity<Response> getAllIngredients() {
        List<IngredientResponseDTO> ingredientResponseDTOList = ingredientService.getAllIngredients();
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
    public ResponseEntity<Response> getIngredientById(@PathVariable Long id) {
        try {
            IngredientResponseDTO ingredientResponseDTO = ingredientService.getIngredientById(id);
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
    public ResponseEntity<Response> insertIngredient(@RequestBody IngredientRequestDTO ingredientRequestDTO) {
        try {
            IngredientResponseDTO ingredientResponseDTO = ingredientService.insertIngredient(ingredientRequestDTO);
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
    public ResponseEntity<Response> updateIngredientById(@PathVariable Long id, @RequestBody IngredientRequestDTO ingredientRequestDTO) {
        try {
            IngredientResponseDTO ingredientResponseDTO = ingredientService.updateIngredientById(id, ingredientRequestDTO);
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
    public ResponseEntity<Response> deleteIngredientById(@PathVariable Long id) {
        try {
            IngredientResponseDTO ingredientResponseDTO = ingredientService.deleteIngredientById(id);
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
