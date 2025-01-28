package com.personal.foodcost.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.MapKeyJavaType;

import java.util.List;
import java.util.Map;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany
    private List<RawMaterialPerDish> rawMaterialList;

    @Column
    private Double price;

    @Column
    private String description;

}
