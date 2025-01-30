package com.personal.foodcost.repositories;

import com.personal.foodcost.entities.RawMaterialPerDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialPerDishRepository extends JpaRepository<RawMaterialPerDish, Long> {
}
