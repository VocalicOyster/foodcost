package com.personal.foodcost.repositories;

import com.personal.foodcost.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {

    @Query("SELECT u FROM MyUser u WHERE u.username = :username")
    Optional<MyUser> findByUsername(@Param("username") String username);
}
