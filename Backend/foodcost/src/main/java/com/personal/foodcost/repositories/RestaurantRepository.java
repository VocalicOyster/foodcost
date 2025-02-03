package com.personal.foodcost.repositories;

import com.personal.foodcost.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE r.pIva = :pIva")
    Optional<Restaurant> findByPIva(@Param("pIva") String pIva);
}
