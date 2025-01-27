package com.personal.foodcost.models.DTOs.Request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDTO {

    private String name;
    private String telephoneNumber;
    private String email;
    private String pIva;
    private String address;
}
