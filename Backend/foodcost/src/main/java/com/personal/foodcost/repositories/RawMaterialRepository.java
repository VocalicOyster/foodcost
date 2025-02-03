package com.personal.foodcost.repositories;

import com.personal.foodcost.entities.Dish;
import com.personal.foodcost.entities.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

    @Query("SELECT r FROM RawMaterial r WHERE r.name = :rawMaterialName")
    Optional<RawMaterial> findByName(@Param("rawMaterialName") String name);
}
