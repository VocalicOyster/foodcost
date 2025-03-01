package com.personal.foodcost.repositories;

import com.personal.foodcost.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("SELECT d FROM Dish d WHERE d.name = :dishName AND d.restName = :restName")
    Optional<Dish> findByName(@Param("dishName") String name, @Param("restName") String restName);

    @Query("SELECT d FROM Dish d WHERE d.restName = :restName")
    List<Dish> findAll(@Param("restName") String restName);

    @Query("SELECT d FROM Dish d WHERE d.restName = :restName AND d.id = :id")
    Optional<Dish> findById(@Param("restName") String restName, @Param("id") Long id);
}
