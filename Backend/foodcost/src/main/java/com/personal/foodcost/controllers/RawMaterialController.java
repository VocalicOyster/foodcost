package com.personal.foodcost.controllers;

import com.personal.foodcost.exceptions.RawMaterialException;
import com.personal.foodcost.filter.JwtAuthFilter;
import com.personal.foodcost.models.DTOs.Response;
import com.personal.foodcost.models.DTOs.ResponseInvalid;
import com.personal.foodcost.models.DTOs.ResponseValid;
import com.personal.foodcost.models.DTOs.request_dto.RawMaterialRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.RawMaterialResponseDTO;
import com.personal.foodcost.models.DTOs.ResponseValidNoData;
import com.personal.foodcost.services.RawMaterialService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/raw")
public class RawMaterialController {

    @Autowired
    private RawMaterialService rawMaterialService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @GetMapping //TUTTI
    public ResponseEntity<Response> getAllRaw(HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);
        List<RawMaterialResponseDTO> rawMaterialResponseDTOList = rawMaterialService.getAllRaw(jwt);

        if (rawMaterialResponseDTOList.isEmpty()) {
            return ResponseEntity.status(204).body(
                    new ResponseValidNoData(
                            204,
                            "No raw material in database"
                    )
            );
        }

        return ResponseEntity.ok().body(
                new ResponseValid(
                        200,
                        "Raw material retrieved!",
                        rawMaterialResponseDTOList
                )
        );
    }

    @GetMapping("/{id}") //TUTTI
    public ResponseEntity<Response> getRawById(@PathVariable Long id, HttpServletRequest request) {
        String jwt = jwtAuthFilter.parseJwt(request);

        try {
            RawMaterialResponseDTO rawMaterialResponseDTO = rawMaterialService.getRawById(id, jwt);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Raw material retrieved!",
                            rawMaterialResponseDTO
                    )
            );
        } catch (RawMaterialException e) {
            return ResponseEntity.status(404).body(
                    new ResponseInvalid(
                            404,
                            "No Raw material retrieved with this id!"
                    )
            );
        }
    }

    @PostMapping //OWNER E ADMIN
    public ResponseEntity<Response> insertRaw(@RequestBody RawMaterialRequestDTO rawMaterialRequestDTO, HttpServletRequest request) {

        String jwt = jwtAuthFilter.parseJwt(request);

        try {
            RawMaterialResponseDTO rawMaterialResponseDTO = rawMaterialService.insertRaw(rawMaterialRequestDTO, jwt);
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Raw material inserted!",
                            rawMaterialResponseDTO
                    )
            );
        } catch (RawMaterialException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

    @PutMapping //OWNER E ADMIN
    public ResponseEntity<Response> updateById(@PathVariable Long id, @RequestBody RawMaterialRequestDTO rawMaterialRequestDTO, HttpServletRequest request) {

        String jwt = jwtAuthFilter.parseJwt(request);

        try {
            RawMaterialResponseDTO rawMaterialResponseDTO = rawMaterialService.updateById(id, rawMaterialRequestDTO, jwt);

            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Raw Material updated!",
                            rawMaterialResponseDTO
                    )
            );
        } catch (RawMaterialException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            e.getMessage()
                    )
            );
        }
    }

    @DeleteMapping //OWNER E ADMIN
    public ResponseEntity<Response> deleteById(@PathVariable Long id, HttpServletRequest request) {

        String jwt = jwtAuthFilter.parseJwt(request);

        try {
            RawMaterialResponseDTO rawMaterialResponseDTO = rawMaterialService.deleteById(id, jwt);

            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Raw Material deleted!",
                            rawMaterialResponseDTO
                    )
            );
        } catch (RawMaterialException e) {
            return ResponseEntity.status(e.getStatusCode()).body(
                    new ResponseInvalid(
                            e.getStatusCode(),
                            "Raw Material updated!"
                    )
            );
        }
    }
}
