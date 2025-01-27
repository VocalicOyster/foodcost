package com.personal.foodcost.models.DTOs.Response;

import com.personal.foodcost.models.DTOs.ROLES;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String name;
    private ROLES role;
}
