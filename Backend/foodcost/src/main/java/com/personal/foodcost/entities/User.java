package com.personal.foodcost.entities;

import com.personal.foodcost.models.DTOs.ROLES;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String cellphone;

    @Column
    private ROLES role;

    @ManyToOne
    private Restaurant restaurant;

}
