package com.personal.foodcost.models.DTOs.response_dto;

import com.personal.foodcost.models.DTOs.ROLES;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private ROLES role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ROLES getRole() {
        return role;
    }

    public void setRole(ROLES role) {
        this.role = role;
    }
}
