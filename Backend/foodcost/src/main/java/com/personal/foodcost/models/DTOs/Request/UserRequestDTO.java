package com.personal.foodcost.models.DTOs.Request;

import com.personal.foodcost.entities.Restaurant;
import com.personal.foodcost.models.DTOs.ROLES;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String name;

    private String username;

    private String password;
    private String RepeatPass;

    private ROLES role;

    private Restaurant restaurant;
}
