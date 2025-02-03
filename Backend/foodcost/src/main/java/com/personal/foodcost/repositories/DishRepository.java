package com.personal.foodcost.repositories;

import com.personal.foodcost.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("SELECT d FROM Dish d WHERE d.name = :dishName")
    Optional<Dish> findByName(@Param("dishName") String name);
}
